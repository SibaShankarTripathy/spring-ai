package com.ai.data;

import java.util.Arrays;
import java.util.List;

import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.reader.TextReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

/*
 * This class and contains method is responsible to convert the document in to chunks.
 * These chunks will store in Vector DB. 
 */

@Component
public class DocumentsDetails {

	// To see the dash board of Qdrant
	
	// Getting object of Vector DB
	@Autowired
	private VectorStore vectorStore;

	@Autowired
	private EmbeddingModel embeddingModel;

	public String saveDocument() {
		String response = "";
		try {
			// Read the source document which is available in resource folder path of this project
			TextReader textReader = new TextReader(new ClassPathResource("job_listings.txt"));

			// Splitting document in chunks way - 1
			// Create list of special character should be part of chunks.
//			List<Character> retainSeparaterList = List.of(',',' ','-','_');
			// Configure the structure to create chunks
//			TokenTextSplitter textSplitter = new TokenTextSplitter(100,100,5,1000,true,retainSeparaterList);
			// Split/Convert documented all text(job_listings.txt) into small text chunks.
//			List<Document> splittedTextDocument = textSplitter.split(textReader.get());

			// Splitting document in chunks way - 2
			String content = textReader.get().get(0).getText();
			String[] jobs = content.split("\\n\\n(?=\\d+\\.)");
			List<Document> splittedTextDocument = Arrays.stream(jobs).map(Document::new).toList();
			// Just to show what data is storing in DB
			toDisplayChunksBeforeStore(splittedTextDocument);

			// Store the splitted document/chunks in Vector DB.
			// Internally .add() -> doAdd() method will embedded the chunk and store both chunk and data.
			vectorStore.add(splittedTextDocument);
			response = "Vector DB insertion completed.....";

		} catch (Exception e) {
			response = "DB insertion failed.....";
			e.printStackTrace();
		}
		return response;
	}

	private void toDisplayChunksBeforeStore(List<Document> splittedTextDocument) {
		for (Document doc : splittedTextDocument) {
			float[] embedding = embeddingModel.embed(doc.getText());
			System.out.println("\n================================");
			System.out.println("Default embedding model (mxbai-embed-large) array Size : " + embedding.length);
			System.out.println("Chunk ID : " + doc.getId());
			System.out.println("Chunk Preview : ");
			System.out.println(doc.getText());
			System.out.println("Embedding Values of chunk : [");
			for (float val : embedding)
				System.out.print(val + " ");
			System.out.println("]\n================================");
		}
	}
}



/*
 * Equivalent Requests

PUT /collections/myJobDetails 
{
  "vectors": {
    "": {
      "size": 1024,
      "distance": "Cosine",
      "on_disk": false,
      "hnsw_config": {
        "m": 24,
        "payload_m": 24,
        "ef_construct": 256
      },
      "datatype": "float32"
    }
  }
}
*/
