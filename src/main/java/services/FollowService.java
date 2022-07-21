package services;

import java.util.ArrayList;
import java.util.List;

import constants.JpaConst;
import models.Follow;

public class FollowService extends ServiceBase {

    /**
     * follower_idを引数として、そのfollower_idにフォローされているfollowed_idのリストを返す。
     * @param follower_id
     * @return followed_idのリストを返す。
     */
    public List<Integer> getByFollowerId(Integer follower_id) {

        // 引数で与えられたfollower_idをもつFollowのリストを取得する
        List<Follow> follows = em.createNamedQuery(JpaConst.Q_FOL_GET_BY_FOLER_ID, Follow.class)
                .setParameter(JpaConst.JPQL_PARAM_FOLER_ID, follower_id)
                .getResultList();

        ArrayList<Integer> followed_ids = new ArrayList<>();
        for (var f : follows) {
            followed_ids.add(f.getFollowed_id());
        }
        return followed_ids;
    }

    /**
     *  followエンティティをfollowテーブルに登録する(後々、バリデーションを作る必要があるかも。例えば、idが存在しているかをチェックする)
     * @param follower_id
     * @param followed_id
     */
    public void create(int follower_id, int followed_id) {
        em.getTransaction().begin();
        Follow follow = new Follow(follower_id,followed_id);
        em.persist(follow);
        em.getTransaction().commit();
    }

    /**
     * followエンティティをfollowテーブルから削除する(後々、バリデーションを作る必要があるかも。例えば、idが存在しているかをチェックする)
     * @param follower_id
     * @param followed_id
     */
    public void destroy(int follower_id, int followed_id) {
        em.getTransaction().begin();
        Follow follow = findOneInternal(follower_id, followed_id);
        em.remove(follow);
        em.getTransaction().commit();
    }

    /**
     * follower_idとfollowed_idを条件にデータを1件取得し、Employeeのインスタンスで返却する
     * @param follower_id
     * @param followed_id
     * @return Employeeインスタンス
     */
    private Follow findOneInternal(int follower_id, int followed_id) {
        Follow follow = em.createNamedQuery(JpaConst.Q_FOL_GET_BY_FOLER_AND_FOLED_ID, Follow.class)
                .setParameter(JpaConst.JPQL_PARAM_FOLER_ID, follower_id)
                .setParameter(JpaConst.JPQL_PARAM_FOLED_ID, followed_id)
                .getSingleResult();

        return follow;
    }
}
