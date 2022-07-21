package actions;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;

import actions.views.EmployeeView;
import constants.AttributeConst;
import constants.ForwardConst;
import constants.JpaConst;
import services.EmployeeService;
import services.FollowService;

public class FollowAction extends ActionBase {

    private FollowService followService;
    private EmployeeService employeeService;

    @Override
    public void process() throws ServletException, IOException {
        followService = new FollowService();
        employeeService = new EmployeeService();

        //メソッドを実行
        invoke();

        followService.close();
        employeeService.close();
    }

    /**
     * ユーザーの一覧とフォロー状態を表示する。
     * @throws ServletException
     * @throws IOException
     */
    public void index() throws ServletException, IOException {

        //セッションからログイン中の従業員情報を取得
        EmployeeView ev = (EmployeeView) getSessionScope(AttributeConst.LOGIN_EMP);

        //ログインしているユーザがフォローしているユーザのidのリストを取得。
        List<Integer> followed_ids = followService.getByFollowerId(ev.getId());

        //指定されたページ数の一覧画面に表示するデータを取得
        int page = getPage();
        List<EmployeeView> employees = employeeService.getPerPage(page);

        //全ての従業員データの件数を取得
        long employeeCount = employeeService.countAll();

        putRequestScope(AttributeConst.FOL_FOLED_IDS, followed_ids);// フォロー中のidのリスト
        putRequestScope(AttributeConst.EMPLOYEES, employees); //取得した従業員データ
        putRequestScope(AttributeConst.EMP_COUNT, employeeCount); //全ての従業員データの件数
        putRequestScope(AttributeConst.PAGE, page); //ページ数
        putRequestScope(AttributeConst.MAX_ROW, JpaConst.ROW_PER_PAGE); //1ページに表示するレコードの数

        //セッションにフラッシュメッセージが設定されている場合はリクエストスコープに移し替え、セッションからは削除する
        String flush = getSessionScope(AttributeConst.FLUSH);
        if (flush != null) {
            putRequestScope(AttributeConst.FLUSH, flush);
            removeSessionScope(AttributeConst.FLUSH);
        }

        forward(ForwardConst.FW_FOL_INDEX);
    }

    /**
     * followテーブルにレコードを登録する。(ユーザをフォローする)
     * @throws ServletException
     * @throws IOException
     */
    public void create() throws ServletException, IOException {

        //ログインしているユーザの情報を取得
        EmployeeView ev = (EmployeeView) getSessionScope(AttributeConst.LOGIN_EMP);

        //followテーブルにレコードを登録
        followService.create(ev.getId(), Integer.parseInt(getRequestParam(AttributeConst.EMP_ID)));

        //一覧画面にリダイレクト
        redirect(ForwardConst.ACT_FOL, ForwardConst.CMD_INDEX);
    }

    /**
     * followテーブルからレコードを削除する。(フォロー解除する)
     * @throws ServletException
     * @throws IOException
     */
    public void destroy() throws ServletException, IOException {

        //ログインしているユーザの情報を取得
        EmployeeView ev = (EmployeeView) getSessionScope(AttributeConst.LOGIN_EMP);

        //followテーブルからレコードを削除
        followService.destroy(ev.getId(), Integer.parseInt(getRequestParam(AttributeConst.EMP_ID)));

        //一覧画面にリダイレクト
        redirect(ForwardConst.ACT_FOL, ForwardConst.CMD_INDEX);
    }

}
