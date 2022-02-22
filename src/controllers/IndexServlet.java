package controllers;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;

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

		// データベースからメッセージ一覧を取得
        List<Message> messages = em.createNamedQuery("getAllMessages", Message.class).getResultList();
		
        em.close();
		
		// メッセージ一覧（messages）をリクエストスコープにセット
		request.setAttribute("messages", messages);
		
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