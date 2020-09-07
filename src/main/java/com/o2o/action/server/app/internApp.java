package com.o2o.action.server.app;

import com.google.actions.api.ActionRequest;
import com.google.actions.api.ActionResponse;
import com.google.actions.api.DialogflowApp;
import com.google.actions.api.ForIntent;
import com.google.actions.api.response.ResponseBuilder;
import com.google.actions.api.response.helperintent.SelectionCarousel;
import com.google.api.services.actions_fulfillment.v2.model.BasicCard;
import com.google.api.services.actions_fulfillment.v2.model.Button;
import com.google.api.services.actions_fulfillment.v2.model.CarouselSelectCarouselItem;
import com.google.api.services.actions_fulfillment.v2.model.Image;
import com.google.api.services.actions_fulfillment.v2.model.OptionInfo;
import com.google.api.services.actions_fulfillment.v2.model.OpenUrlAction;
import com.google.api.services.actions_fulfillment.v2.model.SimpleResponse;
import com.o2o.action.server.util.CommonUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class internApp extends DialogflowApp {
	@ForIntent("Default Welcome Intent")
	public ActionResponse defaultWelcome(ActionRequest request) throws ExecutionException, InterruptedException {
		ResponseBuilder responseBuilder = getResponseBuilder(request);
		Map<String, Object> data = responseBuilder.getConversationData();

		data.clear();

//		List<String> suggestions = new ArrayList<String>();
		SimpleResponse simpleResponse = new SimpleResponse();
		BasicCard basicCard = new BasicCard();

		simpleResponse.setTextToSpeech("안녕하세요, 도서관에서 도서를 찾아드릴게요." +
				"어떤 도서관에서 책을 찾아볼까요?")
//				.setDisplayText("안녕하세요, 도서관에서 도서를 찾아드릴게요. " +
//						"어떤 도서관에서 책을 찾아볼까요?")
		;
		basicCard
				.setTitle("도서검색도우미")
				.setFormattedText("검색을 원하시는 도서관 이름을 말씀해주세요.")
				.setImage(new Image().setUrl("https://actions.o2o.kr/devsvr4/templates/image/library_welcome.jfif")
						.setAccessibilityText("도서관"));

		responseBuilder.add(simpleResponse);
		responseBuilder.add(basicCard);

		responseBuilder.addSuggestions(new String[] {"강서도서관", "서초구립반포도서관", "강남구립논현도서관"});
		return responseBuilder.build();
	}

	@ForIntent("next")
	public ActionResponse nextIntent(ActionRequest request) throws ExecutionException, InterruptedException {
		ResponseBuilder rb = getResponseBuilder(request);
		Map<String, Object> data = rb.getConversationData();

		data.clear();

		List<String> suggestions = new ArrayList<String>();
		SimpleResponse simpleResponse = new SimpleResponse();
		BasicCard basicCard = new BasicCard();

		simpleResponse.setTextToSpeech("다음입니다. 넥스트.")
				.setDisplayText("다음으로 진행됩니다.")
		;
		basicCard
				.setTitle("다음 인텐트")
				.setFormattedText("인텐트 테스트용")
				.setImage(new Image().setUrl("https://actions.o2o.kr/devsvr4/templates/image/library_welcome.jfif")
						.setAccessibilityText("home"));

		rb.add(simpleResponse);
		rb.add(basicCard);

		rb.addSuggestions(suggestions.toArray(new String[suggestions.size()]));
		return rb.build();
	}

	@ForIntent("select")
	public ActionResponse selectMenu(ActionRequest request) throws ExecutionException, InterruptedException {
		ResponseBuilder rb = getResponseBuilder(request);
		Map<String, Object> data = rb.getConversationData();

		data.clear();

		List<String> suggestions = new ArrayList<String>();
		SimpleResponse simpleResponse = new SimpleResponse();
		BasicCard basicCard = new BasicCard();

		simpleResponse.setTextToSpeech("어떤 메뉴를 선택하시겠습니까?")
				.setDisplayText("어떤 메뉴를 선택하시겠습니까?")
		;


		rb.add(simpleResponse);
		rb.add(basicCard);

		rb.addSuggestions(suggestions.toArray(new String[suggestions.size()]));
		return rb.build();
	}

	@ForIntent("result")
	public ActionResponse result(ActionRequest request) throws ExecutionException, InterruptedException {
		ResponseBuilder rb = getResponseBuilder(request);
		Map<String, Object> data = rb.getConversationData();

		data.clear();

		List<String> suggestions = new ArrayList<String>();
		SimpleResponse simpleResponse = new SimpleResponse();
		BasicCard basicCard = new BasicCard();
		String parameter = CommonUtil.makeSafeString(request.getParameter("menus"));
		//parameter 값을 어디서 가지고 오는건지 follow intent 라 가능한건지

		simpleResponse.setTextToSpeech(parameter + "을 선택하셨습니다.");

		basicCard
				.setTitle(parameter)
				.setFormattedText(parameter + " 인텐트 테스트용")
				.setImage(new Image().setUrl("https://actions.o2o.kr/devsvr4/templates/image/"+parameter+".png")
						.setAccessibilityText(parameter));

		rb.add(simpleResponse);
		rb.add(basicCard);

		rb.addSuggestions(suggestions.toArray(new String[suggestions.size()]));
		return rb.build();
	}

	@ForIntent("Exit Conversation")
	public ActionResponse exit(ActionRequest request) {
		ResponseBuilder responseBuilder = getResponseBuilder(request);

		SimpleResponse simpleResponse = new SimpleResponse();
		simpleResponse.setTextToSpeech("안녕히가세요")
				.setDisplayText("Okay, talk to you next time!");
		responseBuilder.add(simpleResponse);
		responseBuilder.endConversation();
		return responseBuilder.build();
	}
}

