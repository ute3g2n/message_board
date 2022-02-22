package controllers;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Message;
import utils.DBUtil;

@WebServlet("/index")
public class IndexServlet extends HttpServlet {
        private static final long serialVersionUID = 1L;

    public IndexServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        EntityManager em = DBUtil.createEntityManager();


		// 開くページ数を取得（デフォルトは1ページ目）
		int page = 1;

		// 数値ではないものを Integer.parseInt に渡すと NumberFormatException という例外が表示される
		try {
			page = Integer.parseInt(request.getParameter("page")); // request.getParameter() で取得できるのはString型
		} catch(NumberFormatException e) {}

		// 最大件数と開始位置を指定してメッセージを取得
        List<Message> messages = em.createNamedQuery("getAllMessages", Message.class)
									.setFirstResult(15 * (page - 1)) // 何件目からデータを取得するか
									.setMaxResults(15) // データの最大取得件数
									.getResultList();

		// 全件数を取得
		long messages_count = (long)em.createNamedQuery("getMessagesCount", Long.class)
									.getSingleResult(); // 1件だけ取得する

		// メッセージ一覧（messages）をリクエストスコープにセット
		request.setAttribute("messages", messages);
		request.setAttribute("messages_count", messages_count); // 全件数
		request.setAttribute("page", page); // ページ

        em.close();

        // フラッシュメッセージがセッションスコープにセットされていたら
        // リクエストスコープに保存する（セッションスコープからは削除）
		if(request.getSession().getAttribute("flush") != null) {
			request.setAttribute("flush", request.getSession().getAttribute("flush"));
			request.getSession().removeAttribute("flush");
		}

		// index.jsp を呼び出す
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/messages/index.jsp");
		rd.forward(request, response);

    }

}