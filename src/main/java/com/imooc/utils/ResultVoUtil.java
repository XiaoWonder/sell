package com.imooc.utils;

import com.imooc.vo.ResultVo;

/**
 * @author 潇
 * @create 2019-06-25 14:14
 */
public class ResultVoUtil {
   public static ResultVo success(Object object){
      ResultVo resultVo=new ResultVo();
      resultVo.setMsg("成功");
      resultVo.setCode(0);
      resultVo.setData(object);
      return resultVo;
   }

   public static ResultVo success(){
       return success(null);
   }

   public static ResultVo error(Integer code,String msg){
       ResultVo resultVo=new ResultVo();
       resultVo.setCode(code);
       resultVo.setMsg(msg);
       return resultVo;
   }

}
