#!/usr/bin/env bash
#创建短信搜索core
bin/sorl create_core -c sms_core

##删除copyField
curl -X POST -H 'Content-type:application/json' --data-binary '{"delete-copy-field":{ "source":"*", "dest":"_text_" }}' http://localhost:8983/solr/sms_core/schema
curl -X POST -H 'Content-type:application/json' --data-binary '{"delete-field":{"name":"sms"}}' http://localhost:8983/solr/sms_core/schema
curl -X POST -H 'Content-type:application/json' --data-binary '{"delete-field":{"name":"sample"}}' http://localhost:8983/solr/sms_core/schema
##增加中文分词依赖，在solrconfig.xml中添加如下
#<!-- 增加对中文分词的支持 -->
#<lib dir="${solr.install.dir:../../../..}/contrib/analysis-extras/lucene-libs/" regex=".*\.jar" />

##添加text_cn FieldType 支持中文分析
curl -X POST -H 'Content-type:application/json' --data-binary '
{
    "add-field-type":{
        "name":"text_cn",
        "class": "solr.TextField",
        "positionIncrementGap": "100",
        "analyzer": {
            "tokenizer":{
                "class": "solr.HMMChineseTokenizerFactory"
            },
            "filters":[
                {
                    "class":"solr.StopFilterFactory",
                    "words":"org/apache/lucene/analysis/cn/smart/stopwords.txt"
                },{
                    "class":"solr.PorterStemFilterFactory"
                }
            ]
        }
    }
}
'http://localhost:8983/solr/sms_core/schema

##添加需要的字段信息
curl -X POST -H 'Content-type:application/json' --data-binary '{"add-field":{"name":"sender","type":"string","stored":"true","indexed":"true","docValues":"true"}}' http://localhost:8983/solr/sms_core/schema
curl -X POST -H 'Content-type:application/json' --data-binary '{"add-field":{"name":"phoneName","type":"string","stored":"true","indexed":"true","docValues":"true"}}' http://localhost:8983/solr/sms_core/schema
curl -X POST -H 'Content-type:application/json' --data-binary '{"add-field":{"name":"template","type":"string","stored":"true","indexed":"false"}}' http://localhost:8983/solr/sms_core/schema
curl -X POST -H 'Content-type:application/json' --data-binary '{"add-field":{"name":"date","type":"string","stored":"true","indexed":"true","docValues":"true"}}' http://localhost:8983/solr/sms_core/schema
curl -X POST -H 'Content-type:application/json' --data-binary '{"add-field":{"name":"sample","type":"text_cn","stored":"true","indexed":"true"}}' http://localhost:8983/solr/sms_core/schema
curl -X POST -H 'Content-type:application/json' --data-binary '{"add-field":{"name":"count","type":"int","stored":"true","indexed":"true","docValues":"true"}}' http://localhost:8983/solr/sms_core/schema
curl -X POST -H 'Content-type:application/json' --data-binary '{"add-field":{"name":"tags","type":"strings","stored":"true","indexed":"true","docValues":"true"}}' http://localhost:8983/solr/sms_core/schema
curl -X POST -H 'Content-type:application/json' --data-binary '{"add-field":{"name":"updateDate","type":"string","stored":"true","indexed":"true","docValues":"true"}}' http://localhost:8983/solr/sms_core/schema
##添加样本数据
java -Dc=sms_core -Dtype=text/csv -jar example\exmpladocs\post.jar -Dparams="separator=%09&f.tags.split=true&f.tags.separator=%2C"

## 清除数据
curl http://localhost:8983/solr/sms_core/update?commit=true&stream.body=%3Cdelete%3E%3Cquery%3E*%3A*%3C%2Fquery%3E%3C%2Fdelete%3E
curl http://localhost:8983/solr/sms_core/update?commit=true&stream.body=<delete>NOT date:20160829 AND count:1</delete>

## 搜索例子
    #根据电话号码名称
    http://localhost:8983/solr/sms_core/select?indent=on&q=phoneName:腾讯科技*&wt=json&sort=count desc