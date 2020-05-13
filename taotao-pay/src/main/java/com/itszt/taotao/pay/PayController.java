package com.itszt.taotao.pay;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static com.itszt.taotao.pay.AlipayConfig.*;

@Controller
public class PayController {

//    public void payOrder(OrderBean orderBean){
//
//    }
    //付款
    @RequestMapping("payOrder")
    public void payOrder(HttpServletResponse httpResponse) throws Exception {


        AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do", app_id, merchant_private_key, "json", charset, alipay_public_key, sign_type); //获得初始化的AlipayClient
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();//创建API对应的request
        alipayRequest.setReturnUrl(return_url);
        alipayRequest.setNotifyUrl(notify_url);//在公共参数中设置回跳和通知地址
        String orderid=UtilGenOrderID.genOrderID("1", "11", "11", "1108611");

        //商户订单号，商户网站订单系统中唯一订单号，必填

//        String out_trade_no = new String(orderid.getBytes("ISO-8859-1"),"UTF-8");
        String out_trade_no = new String(orderid.getBytes("ISO-8859-1"),"UTF-8");
        System.out.println(orderid);
        //付款金额，必填
        String total_amount = new String("0.01".getBytes("ISO-8859-1"),"UTF-8");
        //订单名称，必填
        String subject = new String("嗑瓜子喝汽水");
        //商品描述，可空
        String body = new String("");

        alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
                + "\"total_amount\":\""+ total_amount +"\","
                + "\"subject\":\""+ subject +"\","
                + "\"body\":\""+ body +"\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");

//        alipayRequest.setBizContent("{" +
//                "    \"out_trade_no\":\""+orderid+"\"," +
//                "    \"product_code\":\"FAST_INSTANT_TRADE_PAY\"," +
//                "    \"total_amount\":0.01," +
//                "    \"subject\":\"瓜子和汽水\"," +
//                "    \"body\":\"瓜子和汽水\"" +
//                "  }");//填充业务参数
        String form="";

        form = alipayClient.pageExecute(alipayRequest).getBody(); //调用SDK生成表单

        httpResponse.setContentType("text/html;charset=utf-8");
        httpResponse.getWriter().write(form);//直接将完整的表单html输出到页面
        httpResponse.getWriter().flush();
        httpResponse.getWriter().close();

    }


    //退款
    @RequestMapping("refund")
    public void refund(HttpServletResponse response) throws Exception {

        response.setCharacterEncoding("UTF-8");

        //获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);

        //设置请求参数
        AlipayTradeRefundRequest alipayRequest = new AlipayTradeRefundRequest();

        //商户订单号，商户网站订单系统中唯一订单号
        String out_trade_no = new String("111112004221022498611");
        //支付宝交易号
        String trade_no = new String("2020042222001456921447883740");
        //请二选一设置
        //需要退款的金额，该金额不能大于订单金额，必填
        String refund_amount = new String("0.01");
        //退款的原因说明
        String refund_reason = new String("用户不要了");
        //标识一次退款请求，同一笔交易多次退款需要保证唯一，如需部分退款，则此参数必传
        String out_request_no = new String("22333");

        alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
                + "\"trade_no\":\""+ trade_no +"\","
                + "\"refund_amount\":\""+ refund_amount +"\","
                + "\"refund_reason\":\""+ refund_reason +"\","
                + "\"out_request_no\":\""+ out_request_no +"\"}");

        //请求
        String result = alipayClient.execute(alipayRequest).getBody();
        //响应的的就是json   可以获取键值 返给指定页面
        //输出

        response.setContentType("text/html;charset=utf-8");
        response.getWriter().println(result);
        response.getWriter().flush();
        response.getWriter().close();
    }

}
