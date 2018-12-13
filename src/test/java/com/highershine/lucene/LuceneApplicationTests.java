package com.highershine.lucene;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StoredField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.nio.file.Paths;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LuceneApplicationTests {

	@Test
	public void createIndex() throws Exception {
		//1、指定索引存放的路径 ，lucene特点，创建一次索引，可多次使用
		//方式有两种，一种是放在内存中的， 也就是我们通过new 的方式去实现，另一种是生成文件。
		//Directory directory=new RAMDirectory();  //内存的方式  RAM 表示内存
		Directory directory= FSDirectory.open(Paths.get("C:\\LucentTest\\luceneIndex"));
		//分析器对象
		Analyzer analyzer = new StandardAnalyzer();
		//创建一个IndexwriterConfig对象
		//第一个参数：lucene的版本，第二个参数：分析器对象
		IndexWriterConfig config=new IndexWriterConfig(analyzer);
		//创建一个Indexwriter对象
		IndexWriter indexWriter = new IndexWriter(directory, config);
		indexWriter.deleteAll();//清除以前的index

		//读取文件信息
		//原始文档存放的目录
		File path = new File("C:\\LucentTest\\luceneFile");
		for (File file : path.listFiles()) {
			if (file.isDirectory()) continue;
			//读取文件信息
			//文件名
			String fileName = file.getName();
			//文件内容
//			String fileContent = FileUtils.readFileToString(file);
			//文件的路径
			String filePath = file.getPath();
			//文件的大小
//			long fileSize = FileUtils.sizeOf(file);
			//创建文档对象
			Document document = new Document();
			//创建域
			//三个参数：1、域的名称2、域的值3、是否存储 Store.YES：存储  Store.NO：不存储
			Field nameField = new TextField("name", fileName, Field.Store.YES);
			Field contentField = new TextField("content", "-----", Field.Store.YES);
			Field pathField  = new StoredField("path", filePath);

			//把域添加到document对象中
			document.add(nameField);
			document.add(contentField);
			document.add(pathField);
			//把document写入索引库
			indexWriter.addDocument(document);
		}
		indexWriter.close();
	}

}
