# netty protoc buffers 集成demo 
  netty是高性能的异步io框架，阿里的dubbo,58系的scf和微博的motan的通信框架，都是选用了netty。
  protoc buf是高性能的序列化方案。
  因此，把他们集成在一起，非常地有必要了。
  
  本demo分别来源于netty和pb的官方手册，目前集成的比较乱，以后应该会重新整理。
  
## maven依赖
```
    <dependencies>
        <dependency>
            <groupId>io.netty</groupId>
            <artifactId>netty-all</artifactId>
            <version>4.0.23.Final</version>
        </dependency>
        <dependency>
            <groupId>com.google.protobuf</groupId>
            <artifactId>protobuf-java</artifactId>
            <version>2.5.0</version>
        </dependency>
    </dependencies>
```

## 代码
这个比较难看，今后更新了再补充。只能这么说，目前能正常跑起来。
