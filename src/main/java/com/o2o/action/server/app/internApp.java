package com.o2o.action.server.app;

import com.google.actions.api.ActionRequest;
import com.google.actions.api.ActionResponse;
import com.google.actions.api.DialogflowApp;
import com.google.actions.api.ForIntent;
import com.google.actions.api.response.ResponseBuilder;
import com.google.actions.api.response.helperintent.SelectionList;
import com.google.api.services.actions_fulfillment.v2.model.*;
import com.o2o.action.server.repository.LibraryRepository;
import com.o2o.action.server.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class internApp extends DialogflowApp {

	//@Autowired
	private LibraryRepository libraryRepository;

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

	@ForIntent("LibrarySelection")
	public ActionResponse librarySelection(ActionRequest request) throws ExecutionException, InterruptedException {
		ResponseBuilder responseBuilder = getResponseBuilder(request);
		Map<String, Object> data = responseBuilder.getConversationData();

		data.clear();

		SimpleResponse simpleResponse = new SimpleResponse();
		BasicCard basicCard = new BasicCard();
		String library = CommonUtil.makeSafeString(request.getParameter("library"));
//		String address = libraryRepository.selectAddress(library);
//		String tel = libraryRepository.selectTel(library); //DB에서 해당 도서관 주소 및 전화번호 가져오기
//		String homepage = libraryRepository.selectHomepage(library); //DB에서 해당 도서관 홈페이지주소 가져오기

		simpleResponse.setTextToSpeech(library + "에서 책을 검색할게요. 책 제목, 작가 이름, 출판사명 중" +
				" 어떤 것으로 검색할까요?")
//				.setDisplayText("다음으로 진행됩니다.")
		;
		basicCard
				.setTitle(library)
				.setSubtitle("도서관 주소 및 전화번호")
//				.setFormattedText(address)
//				.setButtons( //버튼 출력안됨
//						new ArrayList<Button>(
//								Arrays.asList(
//										new Button()
//												.setTitle(library + " 홈페이지")
//												.setOpenUrlAction(
//														new OpenUrlAction().setUrl(homepage)))))
		;

		responseBuilder.add(simpleResponse);
		responseBuilder.add(basicCard);
//		responseBuilder.add("책 제목, 작가 이름, 출판사 명 중 하나를 선택해주세요.");

		responseBuilder.addSuggestions(new String[] {"책 제목", "작가 이름", "출판사명"});
		return responseBuilder.build();
	}

	@ForIntent("Condition")
	public ActionResponse condition(ActionRequest request) throws ExecutionException, InterruptedException {
		ResponseBuilder responseBuilder = getResponseBuilder(request);
		Map<String, Object> data = responseBuilder.getConversationData();

		data.clear();

		SimpleResponse simpleResponse = new SimpleResponse();
		String choice = CommonUtil.makeSafeString(request.getParameter("choice"));

		simpleResponse.setTextToSpeech("검색 조건으로 "+ choice + " 을 선택하셨습니다. 검색어를 말씀해주세요.")
				.setDisplayText("검색 조건으로 "+ choice + " 을 선택하셨습니다. 검색어를 말씀해주세요.")
		;

		responseBuilder.add(simpleResponse);
		return responseBuilder.build();
	}

	@ForIntent("Search")
	public ActionResponse result(ActionRequest request) throws ExecutionException, InterruptedException {
		ResponseBuilder responseBuilder = getResponseBuilder(request);
//		Map<String, Object> data = responseBuilder.getConversationData();
//
//		data.clear();

		BasicCard basicCard = new BasicCard();
		String parameter = CommonUtil.makeSafeString(request.getParameter("menus"));
		//parameter 값을 어디서 가지고 오는건지 follow intent 라 가능한건지

		responseBuilder
				.add("검색 결과")
				.add(
					new SelectionList()
							.setTitle("List Title")
							.setItems(
									Arrays.asList(
											new ListSelectListItem()
													.setTitle("Title of First List Item")
													.setDescription("This is a description of a list item.")
													.setImage(
															new Image()
																	.setUrl(
																			"https://storage.googleapis.com/actionsresources/logo_assistant_2x_64dp.png")
																	.setAccessibilityText("Image alternate text"))
													.setOptionInfo(
															new OptionInfo()
																	.setSynonyms(
																			Arrays.asList("synonym 1", "synonym 2", "synonym 3"))
																	.setKey("SELECTION_KEY_ONE")),
											new ListSelectListItem()
													.setTitle("Google Home")
													.setDescription(
															"Google Home is a voice-activated speaker powered by the Google Assistant.")
													.setImage(
															new Image()
																	.setUrl(
																			"https://storage.googleapis.com/actionsresources/logo_assistant_2x_64dp.png")
																	.setAccessibilityText("Google Home"))
													.setOptionInfo(
															new OptionInfo()
																	.setSynonyms(
																			Arrays.asList(
																					"Google Home Assistant",
																					"Assistant on the Google Home"))
																	.setKey("SELECTION_KEY_GOOGLE_HOME")),
											new ListSelectListItem()
													.setTitle("Google Pixel")
													.setDescription("Pixel. Phone by Google.")
													.setImage(
															new Image()
																	.setUrl(
																			"https://storage.googleapis.com/actionsresources/logo_assistant_2x_64dp.png")
																	.setAccessibilityText("Google Pixel"))
													.setOptionInfo(
															new OptionInfo()
																	.setSynonyms(
																			Arrays.asList("Google Pixel XL", "Pixel", "Pixel XL"))
																	.setKey("SELECTION_KEY_GOOGLE_PIXEL")))));
		return responseBuilder.build();
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

