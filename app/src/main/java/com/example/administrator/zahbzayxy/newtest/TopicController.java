package com.example.administrator.zahbzayxy.newtest;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@SuppressLint("UseSparseArrays")
public class TopicController {

	private HashMap<Integer, String> itemMap;
	private HashMap<Integer, Integer> examMap;
	private HashMap<Integer, HashMap<String, Integer>> totalTopicItemsMap;
	private boolean[] selectedFlags;
	private Context context;
	private ArrayList<Map<String, Object>> topicList;
	private HashMap<Integer, String> selecetedChoice;
	private boolean answerShow;
	private FragmentPagerAdapter answerHideFpa;
	private FragmentPagerAdapter answerShowFpa;
	// 根据测试方式，获得题库:0-顺序；1-随机；2-章节；3-强化；4-测试；5-错题本；6-我的收藏
	private int mode;
	private int subClass; // 章节练习中的章节，强化练习中的类型
	public static final int MODE_SEQUENCE = 0;
	public static final int MODE_RANDOM = 1;
	public static final int MODE_CHAPTERS = 2;
	public static final int MODE_INTENSIFY = 3;
	public static final int MODE_PRACTICE_TEST = 4;
	public static final int MODE_WRONG_TOPIC = 5;
	public static final int MODE_COLLECT = 6;

	public static final int TYPE_CHOICE = 1;
	public static final int TYPE_RW = 2;

	//考试
	private int wrongCount;
	private int rightCount;



	public FragmentPagerAdapter getPagerAdapter(FragmentManager fm,
												TopicFragmentCallBacks topicFragmentCallBacks) {
		return getPagerAdapter(fm,topicFragmentCallBacks);
	}




	public TopicController(Context context, int mode, int subClass) {

		this.context = context;
		this.mode = mode;
		this.subClass = subClass;

		itemMap = new HashMap<Integer, String>();
		itemMap.put(1, "opt1");
		itemMap.put(2, "opt2");
		itemMap.put(3, "opt3");
		itemMap.put(4, "opt4");

		totalTopicItemsMap = new HashMap<Integer, HashMap<String, Integer>>();
		selecetedChoice = new HashMap<Integer, String>();
	}

	public Context getContext() {
		return context;
	}

	public int getMode() {
		return mode;
	}

	public int getSubClass() {
		return subClass;
	}


	public int getWrongCount() {
		return wrongCount;
	}

	public int getRightCount() {
		return rightCount;
	}

	public void countWrong() {
		wrongCount++;
	}

	public void countRight() {
		rightCount++;
	}

	public ArrayList<Map<String, Object>> getTopicList() {
		return topicList;
	}

	public boolean isAnswerShow() {
		return answerShow;
	}

	public void setAnswerShow(boolean answerShow) {
		this.answerShow = answerShow;
	}


	public boolean isDataIdMapNull() {
		if (examMap.size() == 0) {
			return true;
		} else {
			return false;
		}
	}

	public int getDaoId(int id) {
		int returnId = -1;
		switch (mode) {
			case MODE_SEQUENCE: {
				returnId = id;
				break;
			}
			case MODE_RANDOM: {
				returnId = examMap.get(id);
				break;
			}
			case MODE_CHAPTERS:
				break;
			case MODE_INTENSIFY:
				break;
			case MODE_PRACTICE_TEST: {
				returnId = examMap.get(id);
				break;
			}
			case MODE_WRONG_TOPIC: {
				returnId = examMap.get(id);
				break;
			}
			case MODE_COLLECT: {
				returnId = examMap.get(id);
				break;
			}
			default:
				break;
		}
		// System.out.println("" + returnId);
		return returnId;
	}


	public RadioButton getCorrectRadioButton(RadioGroup rg,
											 HashMap<String, Integer> orderMap, String answer) {
		RadioButton returnRb = new RadioButton(context);
		switch (rg.getChildCount()) {
			case 2: {
				RadioButton rb_item1 = (RadioButton) rg.getChildAt(0);
				RadioButton rb_item2 = (RadioButton) rg.getChildAt(1);
				if (String
						.valueOf(
								orderMap.get(rb_item1
										.getText()
										.toString()
										.substring(
												0,
												rb_item1.getText().toString()
														.indexOf(".")))).equals(
								answer)) {
					returnRb = rb_item1;
				} else {
					returnRb = rb_item2;
				}
				break;
			}
			case 4: {
				RadioButton rb_item1 = (RadioButton) rg.getChildAt(0);
				RadioButton rb_item2 = (RadioButton) rg.getChildAt(1);
				RadioButton rb_item3 = (RadioButton) rg.getChildAt(2);
				RadioButton rb_item4 = (RadioButton) rg.getChildAt(3);
				if (String
						.valueOf(
								orderMap.get(rb_item1
										.getText()
										.toString()
										.substring(
												0,
												rb_item1.getText().toString()
														.indexOf(".")))).equals(
								answer)) {
					returnRb = rb_item1;
				} else if (String
						.valueOf(
								orderMap.get(rb_item2
										.getText()
										.toString()
										.substring(
												0,
												rb_item2.getText().toString()
														.indexOf(".")))).equals(
								answer)) {
					returnRb = rb_item2;
				} else if (String
						.valueOf(
								orderMap.get(rb_item3
										.getText()
										.toString()
										.substring(
												0,
												rb_item3.getText().toString()
														.indexOf(".")))).equals(
								answer)) {
					returnRb = rb_item3;
				} else {
					returnRb = rb_item4;
				}
				break;
			}
			default:
				break;
		}
		return returnRb;
	}

	public void setRadioButtonState(RadioGroup rg, boolean bl) {
		switch (rg.getChildCount()) {
			case 2: {
				RadioButton rb_item1 = (RadioButton) rg.getChildAt(0);
				RadioButton rb_item2 = (RadioButton) rg.getChildAt(1);
				rb_item1.setEnabled(bl);
				rb_item2.setEnabled(bl);
				break;
			}
			case 4: {
				RadioButton rb_item1 = (RadioButton) rg.getChildAt(0);
				RadioButton rb_item2 = (RadioButton) rg.getChildAt(1);
				RadioButton rb_item3 = (RadioButton) rg.getChildAt(2);
				RadioButton rb_item4 = (RadioButton) rg.getChildAt(3);
				rb_item1.setEnabled(bl);
				rb_item2.setEnabled(bl);
				rb_item3.setEnabled(bl);
				rb_item4.setEnabled(bl);
				break;
			}
			default:
				break;
		}
	}


//	public ExamResultEntry getThisTestScore() {
//		return examResultService.getThisTestScore();
//	}

	public HashMap<String, Integer> getOrderMap(int topicType) {
		HashMap<String, Integer> orderMap = new HashMap<String, Integer>();
		ArrayList<Integer> tempList = new ArrayList<Integer>();
		if (topicType == TYPE_CHOICE) {
			tempList.add(1);
			tempList.add(2);
			tempList.add(3);
			tempList.add(4);
			Random random = new Random();
			int size = tempList.size();
			int sizeNumber;
			int tempInt;
			int count = 1;
			while (size > 0) {
				sizeNumber = random.nextInt(size);
				tempInt = count + 64;
				orderMap.put(String.valueOf((char) tempInt),
						tempList.get(sizeNumber));
				tempList.remove(sizeNumber);
				size = tempList.size();
				count++;
			}
		} else {
			orderMap.put("A", 1);
			orderMap.put("B", 2);
		}

		return orderMap;
	}

	public String getItemValue(Integer tempInt) {
		return itemMap.get(tempInt);
	}






	public void setSelectedFlag(int position, boolean flag) {
		selectedFlags[position] = flag;
	}

	public void setOrderMap(int position, HashMap<String, Integer> orderMap) {
		totalTopicItemsMap.put(position, orderMap);
	}

	public HashMap<String, Integer> getSavedOrderMap(int position) {
		return totalTopicItemsMap.get(position);
	}

	public boolean getSelectedFlag(int position) {
		return selectedFlags[position];
	}

	public void setSelecetedChoice(int position, String selectedChoice) {
		selecetedChoice.put(position, selectedChoice);
	}

	public String getSelecetedChoice(int position) {
		return selecetedChoice.get(position);
	}




}
