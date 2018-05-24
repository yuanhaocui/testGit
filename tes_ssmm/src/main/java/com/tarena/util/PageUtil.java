package com.tarena.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("pageUtil")
public class PageUtil {
	@Value("#{manyProperties['pageSize']}")
	private int pageSize;
	@Value("#{manyProperties['showNum_a']}")
	private int showNum_a;

	public int getPageSize() {
		return pageSize;
	}

	public int getShowNum_a() {
		return showNum_a;
	}

	public List<Integer> getFenYe_a_Num(int currentPage,int pageSize,int totalCount,int totalPage){
		List<Integer> aNum=new ArrayList<Integer>();
		if(totalCount/pageSize>=showNum_a){
			if(currentPage<showNum_a/2+1){
				for(int i=1;i<=showNum_a;i++){
					aNum.add(new Integer(i));
				}
			}else if((totalPage-currentPage)<(showNum_a/2+1)){
				for(int i=1;i<=showNum_a;i++){
					aNum.add(new Integer(totalPage-showNum_a+i));
				}
			}else{
				for(int i=(currentPage-(showNum_a-(showNum_a/2+1)));i<=(currentPage+(showNum_a-(showNum_a/2+1)));i++){
					aNum.add(new Integer(i));
				}
			}
		}else{
			for(int i=1;i<=totalPage;i++){
				aNum.add(new Integer(i));
			}
		}
		return aNum;
	}
}
