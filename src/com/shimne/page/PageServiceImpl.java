package com.shimne.page;

public class PageServiceImpl implements PageService
{
	private PageConfigureController pageConfigureController;

	public PageServiceImpl()
	{
		this.pageConfigureController = new PageConfigureController();
		pageConfigureController.init();
	}

	@Override
	public void build(Pagination<?> pagination)
	{
		if (pagination.getCurrentPageNum() < 1)
		{
			pagination.setCurrentPageNum(1);
		}

		if (pagination.getMaxPageRowCount() == 0)
		{
			pagination.setMaxPageRowCount(pagination.getTotalRowCount());
		}

		execute(pagination);
	}

	private void execute(Pagination<?> pagination)
	{
		StringBuilder pageBodyBuilder = new StringBuilder();

		pagination.setTotalPageNum(pagination.getTotalRowCount() / pagination.getMaxPageRowCount());

		if (pagination.getTotalRowCount() % pagination.getMaxPageRowCount() > 0)
		{
			pagination.setTotalPageNum(pagination.getTotalPageNum() + 1);
		}

		if (pagination.getTotalPageNum() < pagination.getCurrentPageNum())
		{
			pagination.setCurrentPageNum(1);
		}

		pagination.setStartPageRowCount((pagination.getCurrentPageNum() - 1) * pagination.getMaxPageRowCount());
		pagination.setEndPageRowCount(pagination.getStartPageRowCount() + pagination.getMaxPageRowCount());

		if (pagination.getTotalRowCount() < pagination.getEndPageRowCount())
		{
			pagination.setEndPageRowCount(pagination.getTotalRowCount());
		}

		String statPageTemplate = this.pageConfigureController.getConfigByPageName(pagination.getTemplate(), "stat");

		statPageTemplate = statPageTemplate.replace("${pageNum}", String.valueOf(pagination.getCurrentPageNum()));
		statPageTemplate = statPageTemplate.replace("${totalRowCount}", String.valueOf(pagination.getTotalRowCount()));
		statPageTemplate = statPageTemplate.replace("${currentPageNum}", String.valueOf(pagination.getCurrentPageNum()));
		statPageTemplate = statPageTemplate.replace("${totalPageNum}", String.valueOf(pagination.getTotalPageNum()));
		statPageTemplate = statPageTemplate.replace("${maxPageRowCount}", String.valueOf(pagination.getMaxPageRowCount()));

		pageBodyBuilder.append(this.pageConfigureController.getConfigByPageName(pagination.getTemplate(), "top"));
		pageBodyBuilder.append(statPageTemplate);

		if (pagination.getCurrentPageNum() > 1)
		{
			String firstPageTemplate = this.pageConfigureController.getConfigByPageName(pagination.getTemplate(), "first");
			firstPageTemplate = firstPageTemplate.replace("${url}", pagination.getUrl());
			firstPageTemplate = firstPageTemplate.replace("${pageNum}", "1");

			String prevPageTemplate = this.pageConfigureController.getConfigByPageName(pagination.getTemplate(), "prev");
			prevPageTemplate = prevPageTemplate.replace("${url}", pagination.getUrl());
			prevPageTemplate = prevPageTemplate.replace("${pageNum}", String.valueOf(pagination.getCurrentPageNum() - 1));

			pageBodyBuilder.append(firstPageTemplate);
			pageBodyBuilder.append(prevPageTemplate);
		}

		int startOffSetPageNum = pagination.getCurrentPageNum();
		int endOffSetPageNum = pagination.getCurrentPageNum();
		int maxOffSetPageNum = pagination.getMaxOffSetCount();

		do
		{
			if (startOffSetPageNum > 1)
			{
				--startOffSetPageNum;
				--maxOffSetPageNum;
			}

			if (maxOffSetPageNum > 0 && endOffSetPageNum < pagination.getTotalPageNum())
			{
				++endOffSetPageNum;
				--maxOffSetPageNum;
			}
		}
		while((startOffSetPageNum > 1 || endOffSetPageNum < pagination.getTotalPageNum()) && maxOffSetPageNum > 1);

		for (int i = startOffSetPageNum; i <= endOffSetPageNum; i++)
		{
			if (i == pagination.getCurrentPageNum())
			{
				String bodyOnPageTemplate = this.pageConfigureController.getConfigByPageName(pagination.getTemplate(), "body-on");
				bodyOnPageTemplate = bodyOnPageTemplate.replace("${url}", pagination.getUrl());
				bodyOnPageTemplate = bodyOnPageTemplate.replace("${pageNum}", String.valueOf(i));

				pageBodyBuilder.append(bodyOnPageTemplate);
			}
			else
			{
				String bodyOffPageTemplate = this.pageConfigureController.getConfigByPageName(pagination.getTemplate(), "body-off");
				bodyOffPageTemplate = bodyOffPageTemplate.replace("${url}", pagination.getUrl());
				bodyOffPageTemplate = bodyOffPageTemplate.replace("${pageNum}", String.valueOf(i));

				pageBodyBuilder.append(bodyOffPageTemplate);
			}
		}

		if (pagination.getCurrentPageNum() < pagination.getTotalPageNum())
		{
			String nextPageTemplate = this.pageConfigureController.getConfigByPageName(pagination.getTemplate(), "next");
			nextPageTemplate = nextPageTemplate.replace("${url}", pagination.getUrl());
			nextPageTemplate = nextPageTemplate.replace("${pageNum}", String.valueOf(pagination.getCurrentPageNum() + 1));

			String lastPageTemplate = this.pageConfigureController.getConfigByPageName(pagination.getTemplate(), "last");
			lastPageTemplate = lastPageTemplate.replace("${url}", pagination.getUrl());
			lastPageTemplate = lastPageTemplate.replace("${pageNum}", String.valueOf(pagination.getTotalPageNum()));
			lastPageTemplate = lastPageTemplate.replace("${totalPageNum}", String.valueOf(pagination.getTotalPageNum()));

			pageBodyBuilder.append(nextPageTemplate);
			pageBodyBuilder.append(lastPageTemplate);
		}

		String tailPageTemplate = this.pageConfigureController.getConfigByPageName(pagination.getTemplate(), "tail");

		tailPageTemplate = tailPageTemplate.replace("${url}", pagination.getUrl());
		tailPageTemplate = tailPageTemplate.replace("${pageNum}", String.valueOf(pagination.getCurrentPageNum()));
		tailPageTemplate = tailPageTemplate.replace("${currentPageNum}", String.valueOf(pagination.getCurrentPageNum()));

		pageBodyBuilder.append(tailPageTemplate);

		pagination.setPageContents(pageBodyBuilder.toString());
	}

	public static void main(String[] args)
	{
		Pagination<String> pagination = new Pagination<String>();
		pagination.setTotalRowCount(11);
		pagination.setMaxPageRowCount(3);
		pagination.setCurrentPageNum(3);
		pagination.setUrl("?pageNum=${pageNum}");

		PageServiceImpl pageServiceImpl = new PageServiceImpl();

		pageServiceImpl.build(pagination);

		System.out.println(pagination.getPageContents());
	}
}