package controllers;

import java.io.IOException;
import java.sql.Timestamp;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Message;
import utils.DBUtil;

@WebServlet("/new")
public class NewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public NewServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// EntityManager em = DBUtil.createEntityManager();
		// em.getTransaction().begin();

		// // Messageのインスタンスを生成
		// Message m = new Message();

		// // mの各フィールドにデータを代入
		// String title = "taro";
		// m.setTitle(title);

		// String content = "hello";
		// m.setContent(content);

		// Timestamp currentTime = new Timestamp(System.currentTimeMillis());  // 現在の日時を取得
		// m.setCreated_at(currentTime);
		// m.setUpdated_at(currentTime);

		// // データベースに保存
		// em.persist(m); // データベースに保存
		// em.getTransaction().commit(); // データの新規登録を確定（コミット）させる命令

		// // 自動採番されたIDの値を表示
		// response.getWriter().append(Integer.valueOf(m.getId()).toString());

		// em.close();
		
		
		// CSRF対策
		request.setAttribute("_token", request.getSession().getId());
		
		// おまじないとしてのインスタンスを生成
		request.setAttribute("message", new Message());
		
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/messages/new.jsp");
		rd.forward(request, response);
		
	}

}
