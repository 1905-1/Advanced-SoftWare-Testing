1. 微信小程序登陆流程图说明 : 
 
    上图是小程序登陆的官方流程图说明,官方地址 : https://developers.weixin.qq.com/miniprogram/dev/framework/open-ability/login.html
    
2. 主要实现: 
   
    实现了小程序的自定义登陆，将自定义登陆态token返回给小程序作为登陆凭证。用户的信息保存在数据库中，登陆态token缓存在redis中。 
    
    1. 首先从我们的小程序端调用wx.login() ，获取临时凭证code :
        
    2. 模拟使用该code，进行小程序的登陆获取自定义登陆态 token，用postman进行测试 : 
      
    3. 调用我们需要认证的接口，并携带该token进行鉴权，获取到返回信息  : 
    
       

    
