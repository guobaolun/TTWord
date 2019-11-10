package com.snowy.ttword.entity;

import com.snowy.ttword.dao.entity.Sentence;

import java.util.List;

public class Word {

	public static final int FLAG_ALREADY_ADDED = 0;
	public static final int FLAG_NOT_ADDED = 1;
	public static final int FLAG_NO_WORD = 2;

	private String id;
	private String word;
	private String chinese;
	private String usPhonetic;
	private String ukPhonetic;
	private List<Sentence> sentenceList;
	private boolean selected;
	private int flag;

	public Word() {
	}

	public Word(String id, String word, String chinese, String usPhonetic, String ukPhonetic) {
		this.id = id;
		this.word = word;
		this.chinese = chinese;
		this.usPhonetic = usPhonetic;
		this.ukPhonetic = ukPhonetic;

	}

	public List<Sentence> getSentenceList() {
		return sentenceList;
	}

	public void setSentenceList(List<Sentence> sentenceList) {
		this.sentenceList = sentenceList;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public String getChinese() {
		return chinese;
	}

	public void setChinese(String chinese) {
		this.chinese = chinese;
	}

	public String getUsPhonetic() {
		return usPhonetic;
	}

	public void setUsPhonetic(String usPhonetic) {
		this.usPhonetic = usPhonetic;
	}

	public String getUkPhonetic() {
		return ukPhonetic;
	}

	public void setUkPhonetic(String ukPhonetic) {
		this.ukPhonetic = ukPhonetic;
	}


	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}
}
