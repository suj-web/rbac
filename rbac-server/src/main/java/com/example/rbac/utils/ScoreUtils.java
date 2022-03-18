package com.example.rbac.utils;

/**
 * @Author suj
 * @create 2022/3/18
 */
public class ScoreUtils {

    /**
     * 获取员工积分等级
     * @param score
     * @return
     */
    public static int getScoreGrade(Integer score) {
        if(null == score) {
            return 0;
        }
        if(score < 0) {
            return 0;
        } else if(score >= 0 && score < 20) {
            return 1;
        } else if(score >= 20 && score < 40) {
            return 2;
        } else if(score >= 40 && score < 60) {
            return 3;
        } else if(score >= 60 && score < 80) {
            return 4;
        } else {
            return 5;
        }
    }
}
