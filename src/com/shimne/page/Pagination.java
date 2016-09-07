package com.shimne.page;

import java.io.Serializable;
import java.util.List;

public class Pagination<T> implements Serializable
{
	private static final long serialVersionUID = 6800675031413495717L;
	private static final String DEFAULT = "default";

	private int totalRowCount = 0;				// 总的行数
	private int maxPageRowCount = 20;			// 每页显示数量
	private int maxOffSetCount = 10;			// 导航条显示页码数
	private int currentPageNum = 0;				// 当前页码
	private int totalPageNum = 0; 				// 总页数
	private int startPageRowCount = 0;			// 当前也起始行
	private int endPageRowCount = 0;			// 当前页结束行

	private String url = "";					// 链接地址
	private String template = "";				// 模版
	private String pageContents = "";			// 生成的导航

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