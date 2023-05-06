package controller;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.UserDao;
import dto.User;

@WebServlet("/register")
public class RegisterUser extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	String firstname	=req.getParameter("First");
	String lastname	=req.getParameter("Last");
	long mobile	=Long.parseLong(req.getParameter("mobile"));
	String email	=req.getParameter("email");
	String gender	=req.getParameter("gender");
	String password1	=req.getParameter("password1");
	String password2	=req.getParameter("password2");
	Date dob=Date.valueOf(req.getParameter("dob"));
	
	int age=Period.between(dob.toLocalDate(),LocalDate.now()).getYears();
	
	if(password1.equals(password2)){
		
		if(age>18){
			
			User user=new User();
			user.setAge(age);
			user.setFirstname(firstname);
			user.setLastname(lastname);
			user.setEmail(email);
			user.setGender(gender);
			user.setMobile(mobile);
			user.setPassword(password1);
			user.setDob(dob);
			
			UserDao dao=new UserDao();
			dao.save(user);
			
			resp.getWriter().print("<h1 style='color:green>Account created</h1>");
			req.getRequestDispatcher("Home.html").include(req, resp);
			
		}
		else{
			resp.getWriter().print("<h1 style='color:red>Password mismatch</h1>");
			req.getRequestDispatcher("Register.html").include(req, resp);
		}
	}
	else{
		resp.getWriter().print("<h1 style='color:red>Password mismatch</h1>");
		req.getRequestDispatcher("Register.html").include(req, resp);
	}

	
	}
	
}
