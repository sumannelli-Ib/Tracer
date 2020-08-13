package com.monitoring.aspects;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.CodeSignature;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect  
@Component  
public class Tracer_IB {
	
	
	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(Tracer_IB.class);
	File file = new File("D://HDF-MongoDB//Tracer_Act-1//tracer.txt");
	List<String> db_data =null;
	public void filewriter(String data) {
		
		  
		
		 FileWriter fw;
		try {
			fw = new FileWriter(file, true);
			 BufferedWriter bw = new BufferedWriter(fw);
			    bw.write(data);
			    bw.newLine();
			    bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		//Write Content
	
		    
	}
	
	@Before(value = "execution(* com.monitoring.controller.Controller..*(..))")  
	
	public void beforeAdvice(JoinPoint joinPoint) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {  
		 db_data = new  ArrayList<>();
		
		
	HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
	
								
	System.out.println("********************************************");
	filewriter("**************************************************");
	filewriter("The ip address is "+request.getRemoteAddr());
	db_data.add(request.getRemoteAddr());
	
	
	
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	Date date = new Date();
	String req_current_date = sdf.format(date);
	System.out.println();
	logger.info("********************************************");
	filewriter("The request time is "+req_current_date);
	db_data.add(req_current_date);
	 Class clazz = joinPoint.getTarget().getClass();
	 
	 
	 
	 
	 
	 String cls= clazz.toString();
	 String pack = cls.substring(0,cls.lastIndexOf("."));
	 filewriter("The package name is "+pack);
	 db_data.add(pack);
	 filewriter("the class is "+clazz);
	 db_data.add(clazz.toString());
	/* System.out.println("The package name is "+pack);
	 System.out.println("the class is "+clazz);*/
	
	
	
	
	String signature= joinPoint.getSignature().toString();
	String method = signature.substring(signature.lastIndexOf(".")+1);
	filewriter("The URL calling method is "+method);
	db_data.add(method);
	System.out.println("The URL calling method is "+method);
	filewriter("the request URI is "+request.getRequestURI());
	db_data.add(request.getRequestURI());
	System.out.println("the request URI is "+request.getRequestURI());
	
	List<String> payload = getPayload(joinPoint);
	filewriter("The requested body is:  "+payload);
	db_data.add(payload.toString());

	
	}
	 @After(value = "execution(* com.monitoring.controller..*(..))")  
	 //@AfterReturning("execution(* com.howtodoinjava.app.service.impl.EmployeeManagerImpl.*(..))")
	    public void logAroundAllMethods() {  }
	 
	    @AfterReturning(pointcut="execution(* com.monitoring.controller..*(..))", returning="retVal") 
	    public void logAroundGetEmployee(Object retVal) {
	    	
	    	System.out.println("**********************************");
	    	logger.info("**********************************");
	    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	    	Date date = new Date();
	    	String res_current_date = sdf.format(date);
	    	System.out.println("The response time is "+res_current_date);
	    	filewriter("The response time is "+res_current_date);
	    	db_data.add(res_current_date);
	    	System.out.println("The response is "+retVal);
	    	filewriter("The response is "+retVal);
	    	db_data.add(retVal.toString());
	    	Tracer_DB db = new Tracer_DB();
	    	Connection con = db.connection();
	    	db.tableexist(con, "tracer_ib");
	    	List<String> dd = db.insert(con,"tracer_ib",db_data);
	    	
	    	filewriter("**********************************");
	    	System.out.println("**********************************");
	    }
	 private String getRequestUrl(JoinPoint joinPoint, Class clazz) {
	        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
	        Method method = methodSignature.getMethod();
	        PostMapping methodPostMapping = method.getAnnotation(PostMapping.class);
	        RequestMapping requestMapping = (RequestMapping) clazz.getAnnotation(RequestMapping.class);
	        return getPostUrl(requestMapping, methodPostMapping);
	    }
	  private List<String> getPayload(JoinPoint joinPoint) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		  	
		  
		  
		  	List<String> al = new ArrayList<>();
		  	
	        CodeSignature signature = (CodeSignature) joinPoint.getSignature();
	        StringBuilder builder = new StringBuilder();
	        for (int i = 0; i < joinPoint.getArgs().length; i++) {
	            String parameterName = signature.getParameterNames()[i];
	            //builder.append(parameterName);
	           
	           // builder.append(": ");
	            
	            for(Field f:joinPoint.getArgs()[i].getClass().getDeclaredFields()) {
	            	f.setAccessible(true);
	            	
	            	String st =(f.get(joinPoint.getArgs()[i])).toString();
	            	al.add(st);
	            }
	         
	       
	            
	        }
	        
	     
	      
	        return al;
	    }

	    private String getPostUrl(RequestMapping requestMapping, PostMapping postMapping) {
	        String baseUrl = getUrl(requestMapping.value());
	        String endpoint = getUrl(postMapping.value());

	        return baseUrl + endpoint;
	    }

	    private String getUrl(String[] urls) {
	        if (urls.length == 0) return "";
	        else return urls[0];
	    }
}
