package com.KoreaIT.java.JDBCAM.controller;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.KoreaIT.java.JDBCAM.Article;
import com.KoreaIT.java.JDBCAM.service.ArticleService;
import com.KoreaIT.java.JDBCAM.util.DBUtil;
import com.KoreaIT.java.JDBCAM.util.SecSql;
import com.KoreaIT.java.JDBCAM.util.Util;

public class ArticleController {
	Connection conn;
	Scanner sc;

	private ArticleService articleService;

	public ArticleController(Connection conn, Scanner sc) {
		this.conn = conn;
		this.sc = sc;
		this.articleService = new ArticleService(conn);
	}

	public void doWrite() {
		System.out.println("==글쓰기==");
		System.out.print("제목 : ");
		String title = sc.nextLine();
		System.out.print("내용 : ");
		String body = sc.nextLine();

		int id = articleService.doWrite(title, body);

		System.out.println(id + "번 글이 생성되었습니다");

	}

	public void showList() {
		System.out.println("==목록==");

		List<Article> articles = articleService.showList();

		if (articles.size() == 0) {
			System.out.println("게시글이 없습니다");
			return;
		}

		System.out.println("  번호  /   제목  ");
		for (Article article : articles) {
			System.out.printf("  %d     /   %s   \n", article.getId(), article.getTitle());
		}

	}

	public void doModify(String cmd) {

		int id = 0;

		try {
			id = Integer.parseInt(cmd.split(" ")[2]);
		} catch (Exception e) {
			System.out.println("번호는 정수로 입력해");
			return;
		}

		Map<String, Object> foundArticleMap = articleService.foundArticle(id);

		if (foundArticleMap.isEmpty()) {
			System.out.println(id + "번 글은 없습니다.");
			return;
		}

		System.out.println("==수정==");
		System.out.print("새 제목 : ");
		String title = sc.nextLine().trim();
		System.out.println("새 내용 : ");
		String body = sc.nextLine().trim();

		int number = articleService.doUpdate(id, title, body);

		System.out.println(number + "번 글이 수정되었습니다.");

	}

	public void showDetail(String cmd) {

		int id = 0;

		try {
			id = Integer.parseInt(cmd.split(" ")[2]);
		} catch (Exception e) {
			System.out.println("번호는 정수로 입력해");
			return;
		}

		Map<String, Object> ArticleMap = articleService.showDetail(id);

		if (ArticleMap.isEmpty()) {
			System.out.println(id + "번 글은 없습니다.");
			return;
		}
		System.out.println("==상세보기==");

		Article article = new Article(ArticleMap);

		System.out.println("번호 : " + article.getId());
		System.out.println("작성날짜 : " + Util.getNowDate_TimeStr(article.getRegDate()));
		System.out.println("수정날짜 : " + Util.getNowDate_TimeStr(article.getUpdateDate()));
		System.out.println("제목 : " + article.getTitle());
		System.out.println("내용 : " + article.getBody());

	}

	public void doDelete(String cmd) {

		int id = 0;

		try {
			id = Integer.parseInt(cmd.split(" ")[2]);
		} catch (Exception e) {
			System.out.println("번호는 정수로 입력해");
			return;
		}
		Map<String, Object> ArticleMap = articleService.foundArticle(id);

		if (ArticleMap.isEmpty()) {
			System.out.println(id + "번 글은 없습니다.");
			return;
		}

		int number = articleService.doDelete(id, conn);

		System.out.println(number + "번 글이 삭제되었습니다.");

	}
}