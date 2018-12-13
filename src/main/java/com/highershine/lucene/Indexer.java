//package com.highershine.lucene;
//
//import org.apache.lucene.analysis.standard.StandardAnalyzer;
//import org.apache.lucene.document.Document;
//import org.apache.lucene.document.Field;
//import org.apache.lucene.index.IndexWriter;
//import org.apache.lucene.store.Directory;
//import org.apache.lucene.store.FSDirectory;
//import org.apache.lucene.util.Version;
//
//import java.io.File;
//import java.io.FileFilter;
//import java.io.FileReader;
//
///**
// * @Author: 秋叶飘寒风随
// * @Description:
// * @Date: Created in 2018-11-30 下午 3:25
// * @Modified By:
// */
//public class Indexer {
//    private IndexWriter writer = null;
//    public Indexer(String indexDir) throws Exception {
//        Directory dir = FSDirectory.open(new File(indexDir));//打开保存索引目录
//        writer = new IndexWriter(dir, new StandardAnalyzer(Version.LUCENE_30),
//                true, IndexWriter.MaxFieldLength.UNLIMITED);//创建lucene IndexWriter，创建索引工具
//    }
//
//    public void close() throws Exception
//    {
//        writer.close();
//    }
//
//    public int index(String dataDir, FileFilter filter) throws Exception {
//
//        File[] files = new File(dataDir).listFiles();
//        for (File file : files) {//遍历文件目录下所有txt文件，把文件加入索引
//            if(!file.isDirectory() && !file.isHidden() && file.exists()
//                    && (filter == null || filter.accept(file))) {
//                indexFile(file);
//            }
//        }
//        return writer.numDocs();
//    }
//
//    private Document getDocument(File f) {
//        Document doc = new Document();
//        try {
//            doc.add(new Field("content", new FileReader(f)));
//            doc.add(new Field("fileName", f.getName(), Field.Store.YES,
//                    Field.Index.NOT_ANALYZED));
//            doc.add(new Field("filePath", f.getCanonicalPath(),
//                    Field.Store.YES, Field.Index.NOT_ANALYZED));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return doc;
//    }
//
//    public void indexFile(File f) throws Exception {
//        System.out.println("make indexfile is " + f.getCanonicalPath());
//        Document doc = getDocument(f);//创建文件document
//        writer.addDocument(doc);//把当前文件document加入索引
//    }
//
//    public static void main(String[] args) throws Exception {
//
//        String indexDir = "d:\\";// 创建索引的目录
//        String dataDir = "d:\\a\\";// 创建文件目录
//        long begin = System.currentTimeMillis();
//        Indexer index = new Indexer(indexDir);
//        int numIndexed;
//        numIndexed = index.index(dataDir, new TextFilesFilter());
//        long end = System.currentTimeMillis();
//        index.close();
//        System.out.println("all makeindex num is:"+numIndexed+" use time :"+(end-begin));
//    }
//}
//class TextFilesFilter implements FileFilter
//{
//    public boolean accept(File file)
//    {
//        return file.getName().toLowerCase().endsWith(".txt");
//    }
//}