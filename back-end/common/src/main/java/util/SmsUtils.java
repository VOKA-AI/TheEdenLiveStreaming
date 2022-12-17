//package util;
//
//import com.tencentcloudapi.common.Credential;
//import com.tencentcloudapi.common.exception.TencentCloudSDKException;
//import com.tencentcloudapi.common.profile.ClientProfile;
//import com.tencentcloudapi.common.profile.HttpProfile;
//import com.tencentcloudapi.sms.v20210111.SmsClient;
//import com.tencentcloudapi.sms.v20210111.models.SendSmsRequest;
//import com.tencentcloudapi.sms.v20210111.models.SendSmsResponse;
//
///**
// * 腾讯云短信服务
// */
//public class SmsUtils {
//    public static void sendSms(String phone,String phoneCode) {
//        try{
//            // 实例化一个认证对象，入参需要传入腾讯云账户secretId，secretKey,此处还需注意密钥对的保密
//            // 密钥可前往https://console.cloud.tencent.com/cam/capi网站进行获取
//            Credential cred = new Credential("AKIDue7JFuIPGqAD0WljQr1uWrvhCRP5Uc7M", "yf6G9k47tNaXCahX6NN6YkgMmulLegRu");
//            // 实例化一个http选项，可选的，没有特殊需求可以跳过
//            HttpProfile httpProfile = new HttpProfile();
//            httpProfile.setEndpoint("sms.tencentcloudapi.com");
//            // 实例化一个client选项，可选的，没有特殊需求可以跳过
//            ClientProfile clientProfile = new ClientProfile();
//            clientProfile.setHttpProfile(httpProfile);
//            // 实例化要请求产品的client对象,clientProfile是可选的
//            SmsClient client = new SmsClient(cred, "ap-guangzhou", clientProfile);
//
//            // 实例化一个请求对象,每个接口都会对应一个request对象
//            SendSmsRequest req = new SendSmsRequest();
//            String[] phoneNumberSet1 = {"+86"+phone};
//            req.setPhoneNumberSet(phoneNumberSet1);
//            req.setSmsSdkAppId("1400658939");
//            req.setSignName("虾饭菌公众号");
//            req.setTemplateId("1364222");
//            String[] templateParamSet1 = {phoneCode};
//            req.setTemplateParamSet(templateParamSet1);
//
//            // 返回的resp是一个SendSmsResponse的实例，与请求对象对应
//            SendSmsResponse resp = client.SendSms(req);
//            // 输出json格式的字符串回包
//            System.out.println(SendSmsResponse.toJsonString(resp));
//        } catch (TencentCloudSDKException e) {
//            System.out.println(e.toString());
//        }
//    }
//}
