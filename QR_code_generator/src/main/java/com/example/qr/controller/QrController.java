package com.example.qr.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.qr.model.User;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

@Controller
@RequestMapping("/qr")
public class QrController {
	
	
	@ModelAttribute("qr")
	public User user() {
		return new User();
	}
	
	
	@GetMapping
	public String homePage() {
		return "index";  
//		whenever we use thymeleaf and create a controller for that we have to pass the name of the file in the return statement
	}
	
	
	@PostMapping
	public String generateQRCode(@ModelAttribute("qr") User user,Model model) {
		
		try {
			BufferedImage bufferedImage=generateQRCodeImage(user);
			File output=new File("D:\\sts-4.19.1.RELEASE"+user.getFirstName()+".jpg");
			
			ImageIO.write(bufferedImage, "jpg", output);
			
			model.addAttribute("qr",user);
		} catch (WriterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		
		return "redirect:/qr?success";
		
	}


	public BufferedImage generateQRCodeImage(User user) throws WriterException {
		// TODO Auto-generated method stub
		
		StringBuilder str=new StringBuilder();
		str=str.append("First Name:").append(user.getFirstName()).append("| |").append("Last Name:").append(user.getLastName()).append("| |").append("City:").append(user.getCity()).append("| |").append("State:").append(user.getState()).append("| |").append("Pin COde:").append(user.getZipCode());
		
		
		QRCodeWriter codedWriter=new QRCodeWriter();
		
		BitMatrix bitmatrix=codedWriter.encode(str.toString(), BarcodeFormat.QR_CODE, 200, 200);
		
		return MatrixToImageWriter.toBufferedImage(bitmatrix);
	}

}
