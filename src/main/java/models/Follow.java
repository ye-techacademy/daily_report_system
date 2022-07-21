package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import constants.JpaConst;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import models.key.FollowKey;

/**
 * フォローデータのDTOモデル
 *
 */
@Table(name = JpaConst.TABLE_FOL)
@Data //setterとgetterの自動生成(Lombok)
@NoArgsConstructor //引数なしコンストラクタを自動生成する(Lombok)
@AllArgsConstructor //全てのクラスフィールドを引数にもつ引数ありコンストラクタを自動生成する(Lombok)
@Entity
@IdClass(value = FollowKey.class) //follower_idとfollowed_idの2つを合わせて主キーとする
@NamedQueries({
        @NamedQuery(name = JpaConst.Q_FOL_GET_BY_FOLER_ID, query = JpaConst.Q_FOL_GET_BY_FOLER_ID_DEF),
        @NamedQuery(name = JpaConst.Q_FOL_GET_BY_FOLER_AND_FOLED_ID, query = JpaConst.Q_FOL_GET_BY_FOLER_AND_FOLED_ID_DEF)
})
public class Follow {

    /**
     * follower_id
     * フォローしている従業員のID
     */
    @Id
    @Column(name = JpaConst.FOL_COL_FOLER_ID, nullable = false)
    private Integer follower_id;

    /**
     * followed_id
     * フォローされている人のID
     */
    @Id
    @Column(name = JpaConst.FOL_COL_FOLED_ID, nullable = false)
    private Integer followed_id;
}
