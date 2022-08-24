package com.example.barclayspb7d.barclays_project.entities;

import javax.persistence.*;


@Entity
@Table(name="Document")
public class Document {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long docID;
	
	@Column(name="doc_Name")
	private String  docName;
	
	@Column(name="doc_Path")
	private String docPath;
	
	@ManyToOne
	private User user;
	
	public Long getDocId() {
		return docID;
	}
	public void setDocId(Long docID) {
		this.docID = docID;
	}
	public String getDocName() {
		return docName;
	}
	public void setDocName(String docName) {
		this.docName = docName;
	}
	public String getDocPath() {
		return docPath;
	}
	public void setDocPath(String docPath) {
		this.docPath = docPath;
	}
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	@Override
	public String toString() {
		return "Document [docId=" + docID + ", docName=" + docName + ", docPath=" + docPath + "]";
	}
	
	public Document(Long docId, String docName, String docPath) {
		this.docID = docId;
		this.docName = docName;
		this.docPath = docPath;
	}
	
	public Document() {
		
	}
	
}