package com.o2o.action.server.app;

import com.google.actions.api.ActionRequest;
import com.google.actions.api.ActionResponse;
import com.google.actions.api.DialogflowApp;
import com.google.actions.api.ForIntent;
import com.google.actions.api.response.ResponseBuilder;
import com.google.actions.api.response.helperintent.SelectionList;
import com.google.api.services.actions_fulfillment.v2.model.*;
import com.o2o.action.server.api.AvailableApi;
import com.o2o.action.server.api.BookApi;
import com.o2o.action.server.api.IsbnApi;
import com.o2o.action.server.model.BookInfo;
import com.o2o.action.server.model.LibraryInfo;
import com.o2o.action.server.repository.DAO;
import com.o2o.action.server.util.CommonUtil;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class internApp extends DialogflowApp {

//	@Autowired
//	private LibraryController libraryController;

	private DAO reposit = new DAO();
	private int libCode = 0;
	private BookApi bookApi = new BookApi();
	private ArrayList<BookInfo> bookInfos = new ArrayList<>();
	private AvailableApi availableApi = new AvailableApi();


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
				.setImage(new Image().setUrl("https://actions.o2o.kr/devsvr4/library.jpg")
						.setAccessibilityText("도서관"));

		responseBuilder.add(simpleResponse);
		responseBuilder.add(basicCard);

		responseBuilder.addSuggestions(new String[] {"강남도서관", "서초구립반포도서관", "강남구립논현도서관"});
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

		//DB 에서 정보 불러오기 위한 객체
		LibraryInfo libraryInfo = reposit.getLibraryInfo(library);

		String address = libraryInfo.getAddress();
		String tel = libraryInfo.getTel();
		String homepage = libraryInfo.getHomepage();
		String open = libraryInfo.getOpen();
		String closed = libraryInfo.getClosed();
		libCode = libraryInfo.getCode();

		simpleResponse.setTextToSpeech(library + "에서 책을 검색할게요. 책 제목, 작가 이름, 출판사명 중" +
				" 어떤 것으로 검색할까요?")
		;
		basicCard
				.setTitle(library)
				.setSubtitle("도서관 정보")
				.setFormattedText(address + "  \n" + tel + "  \n휴일 : " + closed)
				.setButtons(
						new ArrayList<Button>(
								Arrays.asList(
										new Button()
												.setTitle(library + " 홈페이지")
												.setOpenUrlAction(
														new OpenUrlAction().setUrl(homepage)))))
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

		if(choice.equals("책 제목")) {
			responseBuilder.addSuggestions(new String[] {"나미야 잡화점의 기적", "미움받을 용기", "보건교사 안은영"});
		}
		else if(choice.equals("작가 이름")) {
			responseBuilder.addSuggestions(new String[] {"히가시노 게이고", "백희나", "손원평"});
		}
		else {
			responseBuilder.addSuggestions(new String[] {"창비", "민음사", "문학동네"});
		}

		responseBuilder.add(simpleResponse);
		return responseBuilder.build();
	}

	@ForIntent("Search")
	public ActionResponse result(ActionRequest request) throws Exception {
		ResponseBuilder responseBuilder = getResponseBuilder(request);
		Map<String, Object> data = responseBuilder.getConversationData();

		data.clear();

		String author = CommonUtil.makeSafeString(request.getParameter("author"));
		String title = CommonUtil.makeSafeString(request.getParameter("title"));
		String publisher = CommonUtil.makeSafeString(request.getParameter("publisher"));

		List<ListSelectListItem> listSelectListItems = new ArrayList<>();
		bookInfos = new ArrayList<>();

		if(!CommonUtil.isEmptyString(author)) { //작가로 검색하는 경우
			bookInfos = bookApi.getResultList("author", author, libCode);
		}
		else if(!CommonUtil.isEmptyString(title)) { //제목으로 검색하는 경우
			bookInfos = bookApi.getResultList("title", title, libCode);
		}
		else { //출판사로 검색하는 경우
			bookInfos = bookApi.getResultList("publisher", publisher, libCode);
		}


		if(!bookInfos.isEmpty()) {
			if (bookInfos.size() > 1) {
				for (BookInfo book : bookInfos) {
					listSelectListItems.add(new ListSelectListItem()
							.setTitle(book.getName())
							.setDescription(book.getAuthors() + "  \n" + book.getPublisher() + "(" + book.getPublication_year() + ")")
							.setImage(
									new Image()
											.setUrl(book.getBookImageURL())
											.setAccessibilityText(book.getName()))
							.setOptionInfo(
									new OptionInfo()
											.setKey(book.getBookKey())));
				}

				responseBuilder
						.add(author + title + publisher + " 검색 결과입니다.")
						.add(
								new SelectionList()
										.setTitle(author + title + publisher + " 검색 결과")
										.setItems(listSelectListItems))
				;
			}
			else {
				BookInfo book = bookInfos.get(0);
				listSelectListItems.add(new ListSelectListItem()
						.setTitle(book.getName())
						.setDescription(book.getAuthors() + "  \n" + book.getPublisher() + "(" + book.getPublication_year() + ")")
						.setImage(
								new Image()
										.setUrl(book.getBookImageURL())
										.setAccessibilityText(book.getName()))
						.setOptionInfo(
								new OptionInfo()
										.setKey(book.getBookKey())));

				responseBuilder
						.add(author + title + publisher + " 검색 결과입니다.")
						.add(
								new SelectionList()
										.setTitle(author + title + publisher + " 검색 결과")
										.setItems(listSelectListItems))
				;
			}
		}
		else responseBuilder.add("검색 결과가 존재하지 않습니다.");


		return responseBuilder.build();
	}

	@ForIntent("Select")
	public ActionResponse select(ActionRequest request) throws Exception {
		ResponseBuilder responseBuilder = getResponseBuilder(request);
		String selectedItem = request.getSelectedOption();
		SimpleResponse simpleResponse = new SimpleResponse();
		BasicCard basicCard = new BasicCard();

		for(BookInfo book : bookInfos) {
			if(selectedItem.equals(book.getBookKey())) {
				basicCard
						.setTitle(book.getName())
						.setSubtitle(book.getAuthors())
						.setFormattedText(book.getPublisher() + "(" + book.getPublication_year() + ")  \n" + book.getClass_no()
							+ "  \n대출 가능 여부 : " + availableApi.getAvail(libCode, book.getIsbn())
						)
						.setImage(
								new Image().setUrl(book.getBookImageURL()))
				;
				simpleResponse.setTextToSpeech(book.getName() + "의 상세정보 입니다.");
			}
		}

		return responseBuilder.add(simpleResponse).add(basicCard).add("도서검색도우미를 종료할까요?").build();
	}

	@ForIntent("Exit")
	public ActionResponse exit(ActionRequest request) {
		ResponseBuilder responseBuilder = getResponseBuilder(request);
		responseBuilder.add("도서검색도우미를 종료할게요.");
		responseBuilder.endConversation();
		return responseBuilder.build();
	}
}

