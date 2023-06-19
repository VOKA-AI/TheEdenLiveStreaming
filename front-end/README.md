# 直播网站

![](https://github.com/VOKA-AI/live-web/actions/workflows/unittest.yml/badge.svg?branch=main)
![](https://github.com/VOKA-AI/live-web/actions/workflows/deploy.yml/badge.svg?branch=main)

## 使用说明

1.  git clone 
2.  cd live
3.  npm install -g yarn
4.  yarn install
5.  yarn add
6.  yarn run dev
   
## 关于CI/CD

每次上传代码都会触发单测，并生成单测覆盖率。

另外，发布release，会触发CD，生成docker镜像并上传。

docker镜像版本为`zhangtianxu/live-streaming-vue:[release-name]`，当然也可以直接使用`zhangtianxu/live-streaming-vue:latest`代替。

docker镜像上传后，可以使用k8s完成部署：

`kubectl apply -f https://github.com/VOKA-AI/live-web/blob/main/k8s_deploy.yaml`

由于[k8s_deploy.yaml](https://github.com/VOKA-AI/live-web/blob/main/k8s_deploy.yaml)是private，所以需要下载后再使用。

服务启动后，可以通过命令`kubectl get service`获取LoadBalancer：nginx-service，的`EXTERNAL-IP`，然后访问相应端口即可。

代码更新并发布新的release后，只需要修改[k8s_deploy.yaml](https://github.com/VOKA-AI/live-web/blob/main/k8s_deploy.yaml)中的image版本即可。
比如最新版本为`v1.01`,修改为`zhangtianxu/live-streaming-vue:v1.01`，然后执行 `kubectl apply -f https://github.com/VOKA-AI/live-web/blob/main/k8s_deploy.yaml` 即可完成更新。


## 代码修改

* 如何修改后台服务器地址
    修改`main.ts`中app.provide的`backendHost`和`vite.config.ts`中的server proxy
