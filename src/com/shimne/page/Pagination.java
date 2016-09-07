package com.shimne.page;

import java.io.Serializable;
import java.util.List;

public class Pagination<T> implements Serializable
{
	private static final long serialVersionUID = 6800675031413495717L;
	private static final String DEFAULT = "default";

	private int totalRowCount = 0;				// �ܵ�����
	private int maxPageRowCount = 20;			// ÿҳ��ʾ����
	private int maxOffSetCount = 10;			// ��������ʾҳ����
	private int currentPageNum = 0;				// ��ǰҳ��
	private int totalPageNum = 0; 				// ��ҳ��
	private int startPageRowCount = 0;			// ��ǰҲ��ʼ��
	private int endPageRowCount = 0;			// ��ǰҳ������

	private String url = "";					// ���ӵ�ַ
	private String template = "";				// ģ��
	private String pageContents = "";			// ���ɵĵ���

	private List<T> datas;

	public Pagination()
	{
		setTemplate(DEFAULT);
	}

	public Pagination(int currentPageNum, int maxPageRowCount, int totalRowCount)
	{
		this.currentPageNum = currentPageNum;
		this.maxPageRowCount = maxPageRowCount;
		this.totalRowCount = totalRowCount;
	}

	public Pagination(int currentPageNum, int maxPageRowCount, int totalRowCount, List<T> datas)
	{
		this.currentPageNum = currentPageNum;
		this.maxPageRowCount = maxPageRowCount;
		this.totalRowCount = totalRowCount;
		this.datas = datas;
	}

	public int getTotalRowCount()
	{
		return totalRowCount;
	}
	public void setTotalRowCount(int totalRowCount)
	{
		this.totalRowCount = totalRowCount;
	}

	public int getMaxPageRowCount()
	{
		return maxPageRowCount;
	}
	public void setMaxPageRowCount(int maxPageRowCount)
	{
		this.maxPageRowCount = maxPageRowCount;
	}

	public int getMaxOffSetCount()
	{
		return maxOffSetCount;
	}
	public void setMaxOffSetCount(int maxOffSetCount)
	{
		this.maxOffSetCount = maxOffSetCount;
	}

	public int getCurrentPageNum()
	{
		return currentPageNum;
	}
	public void setCurrentPageNum(int currentPageNum)
	{
		this.currentPageNum = currentPageNum;
	}

	public int getTotalPageNum()
	{
		return totalPageNum;
	}
	public void setTotalPageNum(int totalPageNum)
	{
		this.totalPageNum = totalPageNum;
	}

	public int getStartPageRowCount()
	{
		return startPageRowCount;
	}
	public void setStartPageRowCount(int startPageRowCount)
	{
		this.startPageRowCount = startPageRowCount;
	}

	public int getEndPageRowCount()
	{
		return endPageRowCount;
	}
	public void setEndPageRowCount(int endPageRowCount)
	{
		this.endPageRowCount = endPageRowCount;
	}

	public String getUrl()
	{
		return url;
	}
	public void setUrl(String url)
	{
		this.url = url;
	}

	public String getTemplate()
	{
		return template;
	}
	public void setTemplate(String template)
	{
		this.template = template;
	}

	public String getPageContents()
	{
		return pageContents;
	}
	public void setPageContents(String pageContents)
	{
		this.pageContents = pageContents;
	}

	public List<T> getDatas()
	{
		return datas;
	}
	public void setDatas(List<T> datas)
	{
		this.datas = datas;
	}
}