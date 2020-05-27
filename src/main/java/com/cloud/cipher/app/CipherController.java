package com.cloud.cipher.app;

import com.cloud.cipher.app.beans.MotpCaptcha;
import com.cloud.cipher.app.beans.RC4CipherImpl;
import com.cloud.cipher.app.beans.Sms;
import com.cloud.cipher.app.token.Token;
import com.cloud.cipher.app.token.TokenRepository;
import com.cloud.cipher.recaptcha.RecaptchaService;
import com.cloud.cipher.recaptcha.RecaptchaUtil;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

@Controller
public class CipherController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TokenRepository tokenRepository;
    @Autowired
    private RecaptchaService recaptchaService;
    private RC4CipherImpl rC4CipherImpl;
    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    public CipherController() throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {
        this.rC4CipherImpl = new RC4CipherImpl();
    }
    
    @GetMapping("/")
    public String login(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("duplicate_error", false);
        return "register";
    }
    
    @PostMapping("/signup")
    public String register(@ModelAttribute User user, Model model) throws IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {
        Optional<User> activeUser = userRepository.findByEmail(user.getEmail());
        if (activeUser.isPresent()) {
            model.addAttribute("duplicate_error", true);
            model.addAttribute("duplicate_msg", "Account with same email already exist");
            return "register";
        } else {
            user.setCipherHash(rC4CipherImpl.encrypt(user.getPassword()));
            user.setPassword("");
            userRepository.save(user);
            return "redirect:/signin";
        }
    }
    
    @GetMapping("/signin")
    public String serveSignin (Model model) {
        model.addAttribute("password_error", false);
        model.addAttribute("account_error", false);
        model.addAttribute("user", new User());
        return "login";
    }
    
    @PostMapping("/signin")
    public String signin (@ModelAttribute User user, Model model) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
        Optional<User> selectedUser = userRepository.findByEmail(user.getEmail());
        if (selectedUser.isPresent()) {
            String password = rC4CipherImpl.decode(selectedUser.get().getCipherHash());
            if (user.getPassword().equals(password)) {
                Token token = new Token();
                token.setUser(selectedUser.get());
                String secretToken = getToken(); 
                token.setToken(secretToken);
                
                RestTemplate rest = new RestTemplate();
                Sms sms = new Sms();
                sms.setApi_token("r2eGKX8Tle5r8F1zI6AJGFsXAnj6A1mHQJr9BbGoQsTM0MHo47Jlsw2kCsnW");
                sms.setFrom("BulkSMSNG");
                sms.setTo(selectedUser.get().getPhone());
                sms.setBody(secretToken + " is your verification code");
                rest.postForEntity("https://www.bulksmsnigeria.com/api/v1/sms/create", sms, Sms.class);
                
                tokenRepository.save(token);
                return "redirect:/motpcaptcha";
            } else {
                model.addAttribute("password_error", true);
                model.addAttribute("msg", "Incorrect password");
                return "login";
            }  
        } else {
            model.addAttribute("account_error", true);
            model.addAttribute("msg", "No account created with email" + user.getEmail());
            return "login";
        }
    }
    
    @GetMapping("/motpcaptcha")
    public String serveMotpCaptcha (Model model) {
       model.addAttribute("captcha", new MotpCaptcha());
       return "MotpCaptcha";
    }
    
    @PostMapping("/motpcaptcha")
    public String authenticate (@RequestParam(name="g-recaptcha-response") String recaptchaResponse, HttpServletRequest request, @ModelAttribute MotpCaptcha captcha, Model model) {
        String ip = request.getRemoteAddr();
        String captchaVerifyMessage = recaptchaService.verifyCapTcha(ip, recaptchaResponse);
        if (!captchaVerifyMessage.equalsIgnoreCase("")) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", captchaVerifyMessage);
            return "error unable to validate user";
        } else {
            Optional<Token> token = tokenRepository.findByToken(captcha.getOtp());
            if (token.isPresent()) {
                tokenRepository.deleteById(token.get().getId());
                return "redirect:/success";
            } else {   
                return "redirect:/motpcaptcha";
            }
        }
    }
    
    @GetMapping("/success")
    public String serveSuccess () {
        return "success";
    }
    
    
    // Random code generator
    public String getToken () {
        String result = "";
        int[] num = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        for (int i = 0; i < 5; i++) {
            Random r = new Random();
            int val = r.nextInt(122);
            if (val < 65 || val > 122) {
                result+= num[(int)(Math.random() * num.length)];
            } else {
                result += (char)val;
            }
        }
        return result;
    }
    
//    @ExceptionHandler({Exception.class})
//    public String databaseError() {
//        return "error-view-name";
//    }
    
}
