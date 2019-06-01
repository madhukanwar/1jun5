package com.example.Greeting;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

@SpringBootApplication
@RestController
public class Application {

	@Value("${server.port}")
	public String port;

	public SimpleDateFormat simpleDateFormat=new SimpleDateFormat("HH:MM:SS");

	public AtomicLong atomicLong=new AtomicLong();

	public static void main(String[] args) throws IOException {

		new SpringApplication(Application.class).run(args);

	}

	@RequestMapping("/{name}")
	public Greeting getName(@PathVariable String name){
		List<String> mylst = Arrays.asList("java","php","dot");
		Random r =new Random();
		String lst= mylst.get(r.nextInt(mylst.size()));
		return new Greeting(port,name,atomicLong.incrementAndGet(),lst,simpleDateFormat.format(new Date()));

	}


}
