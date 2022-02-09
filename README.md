# tiny_demo

## prefix

echo "# tiny_demo" >> README.md 

git init

git add README.md

git commit -m "first commit"

git branch -M main

git remote add origin git@github.com:chenxuange/tiny_demo.git

git push -u origin main



git remote add origin git@github.com:chenxuange/tiny_demo.git

git branch -M main

git push -u origin main

# 权限控制
## 基础的权限控制
基础的权限控制，是采用spring security的默认机制实现的，
即
1、用户登录后，查出该用户的所有权限，存在安全上下文中
2、在需要权限的接口上，利用注解@PreAuthorize定义好需要的权限
3、当用户访问接口时，spring security会将该用户拥有的权限列表与接口上限定的权限进行对比，
如果包含就可以访问，若不包含则拒绝。
缺陷：
必须在需要权限的大串接口上加注解，实际很多且写死了
实际上，每个接口都可以由它的访问路径所唯一确定，基于路径的动态权限控制 

## 基于路径的动态权限控制
请求预检options放行
白名单路径，如swagger相关，登录相关接口需要放行
安全元数据源xxxSecurityMetadataSource:
根据访问路径获取权限列表
访问决策管理器xxxAccessDecisionManager:
没有配置资源的接口一般可以允许直接访问。而配置了资源的接口，则需要将访问所需资源与用户拥有资源进行匹配
