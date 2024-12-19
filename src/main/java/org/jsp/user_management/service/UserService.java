package org.jsp.user_management.service;

import java.util.List;
import java.util.Optional;

import org.jsp.user_management.dao.UserDao;
import org.jsp.user_management.entity.User;
import org.jsp.user_management.entity.UserStatus;
import org.jsp.user_management.exceptionclasses.InvalidCredentialException;
import org.jsp.user_management.responsestructure.ResponseStructure;
import org.jsp.user_management.util.AuthUser;
import org.jsp.user_management.util.MyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class UserService {

	// in userserviceimple sir did i did here only
	@Autowired
	private JavaMailSender javaMailSender;

	@Autowired
	private UserDao userDao;

	public User saveUser(User user) {

//    	String email=user.getEmail();

		user.setOtp(MyUtil.getOtp());
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();

		try {
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

			mimeMessageHelper.addTo(user.getEmail());
			mimeMessageHelper.setSubject("account created");
//			mimeMessageHelper.setText("hello" + user.getName() + "your CDA account has been created successfully");

			//			mimeMessageHelper.setText("<html><body style='background-color:yellow'><h1>Hello " 
//				    + user.getName() 
//				    + ", your CDA account has been created successfully<br><br><hr><br> Your OTP: "
//				    + user.getOtp() 
//				    + "</h1></body></html>", true);

			mimeMessageHelper.setText(
				    "<html>"
				        + "<head>"
				        + "    <style>"
				        + "        body {"
				        + "            font-family: Arial, sans-serif;"
				        + "            background-color: #f4f4f4;"
				        + "            color: #333;"
				        + "            padding: 20px;"
				        + "        }"
				        + "        .email-container {"
				        + "            max-width: 600px;"
				        + "            margin: 20px auto;"
				        + "            background: #ffffff;"
				        + "            padding: 20px;"
				        + "            border-radius: 8px;"
				        + "            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);"
				        + "        }"
				        + "        .header {"
				        + "            background-color: #4CAF50;"
				        + "            color: white;"
				        + "            padding: 10px 15px;"
				        + "            text-align: center;"
				        + "            border-radius: 8px 8px 0 0;"
				        + "        }"
				        + "        .content {"
				        + "            padding: 20px;"
				        + "            text-align: left;"
				        + "        }"
				        + "        .footer {"
				        + "            margin-top: 20px;"
				        + "            text-align: center;"
				        + "            font-size: 12px;"
				        + "            color: #888;"
				        + "        }"
				        + "        .otp {"
				        + "            font-size: 18px;"
				        + "            font-weight: bold;"
				        + "            color: #d9534f;"
				        + "        }"
				        + "    </style>"
				        + "</head>"
				        + "<body>"
				        + "    <div class='email-container'>"
				        + "        <div class='header'>Welcome to CDA</div>"
				        + "        <div class='content'>"
				        + "            <h2>Hello " + user.getName() + ",</h2>"
				        + "            <p>Your CDA account has been successfully created. We're excited to have you on board!</p>"
				        + "            <p>Your One-Time Password (OTP) is:</p>"
				        + "            <div class='otp'>" + user.getOtp() + "</div>"
				        + "            <p>Please use this OTP to complete your registration. If you have any questions, feel free to contact us.</p>"
				        + "        </div>"
				        + "        <div class='footer'>"
				        + "            © 2024 CDA. All rights reserved."
				        + "        </div>"
				        + "    </div>"
				        + "</body>"
				        + "</html>", 
				    true
				);

			javaMailSender.send(mimeMessage);
			
		} catch (MessagingException e) {
			e.printStackTrace();
		}

		return userDao.saveUser(user);
	}

	public Optional<User> getUserById(int id) {
		return userDao.getUserById(id);
	}

	public List<User> findAllUsers() {
		return userDao.findAllUsers();
	}

	public ResponseEntity<ResponseStructure<User>> login(AuthUser auth) {
		Optional<User> optional = userDao.login(auth.getUsername(), auth.getPassword());
		if (optional.isEmpty()) {
			throw InvalidCredentialException.builder().message("Invalid username or password").build();
		}
		User user = optional.get();
		ResponseStructure<User> responseStructure = ResponseStructure.<User>builder().status(HttpStatus.OK.value())
				.message("Login successful").data(user).build();

		return ResponseEntity.status(HttpStatus.OK).body(responseStructure);
	}

	public ResponseEntity<?> verifyOtp(int id, int otp) {
	    Optional<User> optional = userDao.getUserById(id);
	    
	    if (optional.isEmpty()) {
	        throw new RuntimeException("Invalid user ID, unable to verify the OTP");
	    }
	    
	    User user = optional.get();
	    
	    // Check if the provided OTP matches the user's OTP
	    if (otp != user.getOtp()) {
	        throw new RuntimeException("Invalid OTP, unable to verify the OTP");
	    }
	    
	    // Activate the user's account
	    user.setStatus(UserStatus.ACTIVE);
	    
	    // Save the updated user to the database
	    user = userDao.saveUser(user);
	    
	    // Return a successful response
	    return ResponseEntity.status(HttpStatus.OK)
	            .body(ResponseStructure.<User>builder()
	                    .status(HttpStatus.OK.value())
	                    .message("OTP verified successfully, account activated")
	                    .data(user)
	                    .build());
	}

		
		
		
	
}
