package com.KoreaIT.java.JDBCAM;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		System.out.println("==프로그램 시작==");
		Scanner sc = new Scanner(System.in);

		int lastArticleId = 0;

		while (true) {
			System.out.print("명령어 > ");
			String cmd = sc.nextLine().trim();

			if (cmd.equals("exit")) {
				break;
			}

			if (cmd.equals("article write")) {
				System.out.println("==글쓰기==");
				int id = lastArticleId + 1;
				System.out.print("제목 : ");
				String title = sc.nextLine();
				System.out.print("내용 : ");
				String body = sc.nextLine();

				lastArticleId = id;

				System.out.println(id + "번 글이 등록되었습니다");

				Connection conn = null;
				PreparedStatement pstmt = null;

				try {
					Class.forName("com.mysql.jdbc.Driver");
					String url = "jdbc:mysql://127.0.0.1:3306/JDBC_AM?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull";

					conn = DriverManager.getConnection(url, "root", "");
					System.out.println("연결 성공!");

					String sql = "INSERT INTO article ";
					sql += "SET regDate = NOW(),";
					sql += "updateDate = NOW(),";
					sql += "title = '" + title + "',";
					sql += "`body`= '" + body + "';";

					System.out.println(sql);

					pstmt = conn.prepareStatement(sql);

					int affectedRow = pstmt.executeUpdate();

					System.out.println("affectedRow : " + affectedRow);

				} catch (ClassNotFoundException e) {
					System.out.println("드라이버 로딩 실패");
				} catch (SQLException e) {
					System.out.println("에러 : " + e);
				} finally {
					try {
						if (conn != null && !conn.isClosed()) {
							conn.close();
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
					try {
						if (pstmt != null && !pstmt.isClosed()) {
							pstmt.close();
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}

			} else if (cmd.equals("article list")) {
				System.out.println("==목록==");

				Connection conn = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;

				List<Article> articles = new ArrayList<>();

				try {
					Class.forName("com.mysql.jdbc.Driver");
					String url = "jdbc:mysql://127.0.0.1:3306/JDBC_AM?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull";

					conn = DriverManager.getConnection(url, "root", "");
					System.out.println("연결 성공!");

					String sql = "SELECT *";
					sql += " FROM article";
					sql += " ORDER BY id DESC;";

					System.out.println(sql);

					pstmt = conn.prepareStatement(sql);

					rs = pstmt.executeQuery(sql);

					while (rs.next()) {
						int id = rs.getInt("id");
						String regDate = rs.getString("regDate");
						String updateDate = rs.getString("updateDate");
						String title = rs.getString("title");
						String body = rs.getString("body");

						Article article = new Article(id, regDate, updateDate, title, body);

						articles.add(article);

					}
//					for (int i = 0; i < articles.size(); i++) {
//						System.out.println("번호 : " + articles.get(i).getId());
//						System.out.println("등록 날짜 : " + articles.get(i).getRegDate());
//						System.out.println("수정 날짜 : " + articles.get(i).getUpdateDate());
//						System.out.println("제목 : " + articles.get(i).getTitle());
//						System.out.println("내용 : " + articles.get(i).getBody());
//					}

				} catch (ClassNotFoundException e) {
					System.out.println("드라이버 로딩 실패");
				} catch (SQLException e) {
					System.out.println("에러 : " + e);
				} finally {
					try {
						if (rs != null && !rs.isClosed()) {
							rs.close();
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
					try {
						if (pstmt != null && !pstmt.isClosed()) {
							pstmt.close();
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
					try {
						if (conn != null && !conn.isClosed()) {
							conn.close();
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				if (articles.size() == 0) {
					System.out.println("게시글이 없습니다");
					continue;
				}

				System.out.println("  번호  /   제목  ");
				for (Article article : articles) {
					System.out.printf("  %d     /   %s   \n", article.getId(), article.getTitle());
				}
			} else if (cmd.startsWith("article modify")) {
				String[] cmdBits = cmd.split(" ", 3);

				if (cmdBits.length == 2) {
					System.out.println("명령어 확인해줘");
					continue;
				}
				int id = 0;
				try {
					id = Integer.parseInt(cmdBits[2]);
				} catch (Exception e) {
					System.out.println("정수를 입력해");
					continue;
				}

				Connection conn = null;
				PreparedStatement pstmt = null;

				try {
					Class.forName("com.mysql.jdbc.Driver");
					String url = "jdbc:mysql://127.0.0.1:3306/JDBC_AM?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull";

					conn = DriverManager.getConnection(url, "root", "");
					System.out.println("연결 성공!");

					String sql = "UPDATE article ";
					sql += "SET title = ?, ";
					sql += "`body` = ?";
					sql += " WHERE id = ?;";

					System.out.print("새 제목 :");
					String newtitle = sc.nextLine();
					System.out.print("새 제목 :");
					String newbody = sc.nextLine();
					int newId = id;

					System.out.println(sql);

					pstmt = conn.prepareStatement(sql);

					pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, newtitle);
					pstmt.setString(2, newbody);
					pstmt.setInt(3, newId);
					int res = pstmt.executeUpdate();
					if (res > 0) {
						System.out.println("수정 성공");
					} else {
						System.out.println("수정 실패");
					}

				} catch (ClassNotFoundException e) {
					System.out.println("드라이버 로딩 실패");
				} catch (SQLException e) {
					System.out.println("에러 : " + e);
				} finally {
					try {
						if (conn != null && !conn.isClosed()) {
							conn.close();
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
					try {
						if (pstmt != null && !pstmt.isClosed()) {
							pstmt.close();
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}

			} else if (cmd.startsWith("article delete")) {
				String[] cmdBits = cmd.split(" ", 3);

				if (cmdBits.length == 2) {
					System.out.println("명령어 확인해줘");
					continue;
				}
				int findId = 0;
				try {
					findId = Integer.parseInt(cmdBits[2]);
				} catch (Exception e) {
					System.out.println("정수를 입력해");
					continue;
				}
				
				Connection conn = null;
				PreparedStatement pstmt = null;

				try {
					Class.forName("com.mysql.jdbc.Driver");
					String url = "jdbc:mysql://127.0.0.1:3306/JDBC_AM?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull";

					conn = DriverManager.getConnection(url, "root", "");
					System.out.println("연결 성공!");

					String sql = "DELETE FROM article ";
					sql += "WHERE id = ?;";
					
			
					
					int id = findId;
					

					System.out.println(sql);

					pstmt = conn.prepareStatement(sql);

					pstmt = conn.prepareStatement(sql);
					pstmt.setInt(1,findId);
					
					int r = pstmt.executeUpdate(); 
					
					

				} catch (ClassNotFoundException e) {
					System.out.println("드라이버 로딩 실패");
				} catch (SQLException e) {
					System.out.println("에러 : " + e);
				} finally {
					try {
						if (conn != null && !conn.isClosed()) {
							conn.close();
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
					try {
						if (pstmt != null && !pstmt.isClosed()) {
							pstmt.close();
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				
				

			} // delete꺼

			else if (cmd.startsWith("article detail")) {
				
				//파싱
				String[] cmdBits = cmd.split(" ", 3);

				if (cmdBits.length == 2) {
					System.out.println("명령어 확인해줘");
					continue;
				}
				int findId = 0;
				try {
					findId = Integer.parseInt(cmdBits[2]);
				} catch (Exception e) {
					System.out.println("정수를 입력해");
					continue;
				}
				
				Connection conn = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;

				List<Article> articles = new ArrayList<>();

				try {
					Class.forName("com.mysql.jdbc.Driver");
					String url = "jdbc:mysql://127.0.0.1:3306/JDBC_AM?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull";

					conn = DriverManager.getConnection(url, "root", "");
					System.out.println("연결 성공!");

					String sql = "SELECT *";
					sql += " FROM article;";

					

					pstmt = conn.prepareStatement(sql);

					rs = pstmt.executeQuery(sql);

					while (rs.next()) {
						int id = rs.getInt("id");
						String regDate = rs.getString("regDate");
						String updateDate = rs.getString("updateDate");
						String title = rs.getString("title");
						String body = rs.getString("body");

						Article article = new Article(id, regDate, updateDate, title, body);

						articles.add(article);

					}

				} catch (ClassNotFoundException e) {
					System.out.println("드라이버 로딩 실패");
				} catch (SQLException e) {
					System.out.println("에러 : " + e);
				} finally {
					try {
						if (rs != null && !rs.isClosed()) {
							rs.close();
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
					try {
						if (pstmt != null && !pstmt.isClosed()) {
							pstmt.close();
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
					try {
						if (conn != null && !conn.isClosed()) {
							conn.close();
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				if (articles.size() == 0) {
					System.out.println("게시글이 없습니다");
					continue;
				}

				System.out.println("  상세보기 ");
				for (Article article : articles) {
					if (article.getId() == findId) {
						System.out.println("번호 : " + article.getId());
						System.out.println("등록 날짜 : " + article.getRegDate());
						System.out.println("수정 날짜 : " + article.getUpdateDate());
						System.out.println("제목 : " + article.getTitle());
						System.out.println("내용 : " + article.getBody());
					}
				}

			} // detail 꺼

			

			else {
				System.out.println("그런 명령어 없어");
			}

		}

		System.out.println("==프로그램 종료==");

		sc.close();
	}
}