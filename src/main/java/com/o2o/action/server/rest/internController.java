package com.o2o.action.server.rest;

import java.util.concurrent.ExecutionException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.o2o.action.server.api.BookApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.o2o.action.server.app.internApp;
//import com.o2o.action.server.repo.CategoryRepository;
import com.o2o.action.server.util.CommonUtil;

@RestController
public class internController {
	private final internApp internApp;
	private BookApi bookApi;

	@Autowired
	//private CategoryRepository categoryRepository;

	public internController() {
		internApp = new internApp();
	}

	@RequestMapping(value = "/intern", method = RequestMethod.POST)
	public @ResponseBody String processActions(@RequestBody String body, HttpServletRequest request,
			HttpServletResponse response) {
		String jsonResponse = null;
		try {
			//System.out.println("request : " + body + "," + categoryRepository);
			System.out.println("request : " + body );
			jsonResponse = internApp.handleRequest(body, CommonUtil.getHttpHeadersMap(request)).get();
			System.out.println("response : " + jsonResponse);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}

		return jsonResponse;
	}

//	@GetMapping("/isbn/title/{title}")
//	public BookIsbn getIsbnByTitle(@RequestParam String title) throws ParseException {
//		return libraryApi.getIsbnByTitle(title);
//	}
}
