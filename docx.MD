## `Required Modules In RevoTube`
* **indexing**
    * `take input from rabbitmq`
    * `index media`
    * `index user`
    * `provide search on media`
    * `provide search on user`
    * `provide delete or reindex on media`
    * `provide delete or reindex on user`
* **feedback**
    * `store media-user likes`
    * `store media-user comments`
    * `store media-user views`
    * `store user-user followers`
    * `store and cache views count`
    * `store and cache likes count`
    * `store and cache followers count`
* **torrent**
    * `download torrent .mp4 files`
* **notification**
    * `notifiy user with new media`
* **payment**
    * `add payment`
* **ffmpeg**
    * `add multiple quality to video `
    
    
   ## `Locations`
* 1 -ashraf1revo@gmail.com-->**eureka**    **config**
* 2 -ashraf2revo@gmail.com-->**auth**      **zuul**
* 3 -ashraf3revo@gmail.com-->**tube**      **bento4**    
* 4 -ashraf4revo@gmail.com-->**indexing**  **feedback**



* `authmongo`           mongodb://CloudFoundry_7njjtl29_n4b5s4hv_dcnhd3vj:SfigB5dZDmKJiGBk00C_Nz9hmxZKtMjI@ds235065.mlab.com:35065/CloudFoundry_7njjtl29_n4b5s4hv
* `feedbackmongo`       mongodb://CloudFoundry_7njjtl29_v8vn3s1p_6l0r5g0b:Hk842VXifO2P1GtnzWqIoUkEIfxEhDik@ds235775.mlab.com:35775/CloudFoundry_7njjtl29_v8vn3s1p
* `tubemongo`           mongodb://CloudFoundry_7njjtl29_dknlgqb0_97alqd62:SYnP4W97T4R5cPEJF4JxoaNJdkLtYCCh@ds235775.mlab.com:35775/CloudFoundry_7njjtl29_dknlgqb0

* `queue`             rhino.rmq.cloudamqp.com       rlgahcfz      M_Bbzo_sQtkKofEHrW_KcTA5h2U43-bq
* `authredis`   redis-16411.c15.us-east-1-4.ec2.cloud.redislabs.com   rkorp6odDMPssu8w   16411

* `indexingelastic`    http://cloudfoundry:a4976a524ad629c319aa80459c73bc07@nori-us-east-1.searchly.com