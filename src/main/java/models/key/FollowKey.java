package models.key;

import java.io.Serializable;

import lombok.Data;

/**
 * フォローテーブルの主キー用Entity
 * follower_idとfollowed_idの2つを合わせて主キーとする
 *
 */
@Data
public class FollowKey implements Serializable {

    /**
     * follower_id
     */
    private Integer follower_id;

    /**
     * followed_id
     */
    private Integer followed_id;
}
