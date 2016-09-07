package com.shimne.page;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.helpers.FileWatchdog;
import org.apache.log4j.helpers.LogLog;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import com.shimne.exception.AOException;
import com.shimne.util.ObjectUtil;

public class PageConfigureController
{
	private Map<String, String> pageMap;
	private Map<String, Element> pageElementMap;

	public void init()
	{
		String configFile = "com/shimne/page/default-page.xml";

		if (Thread.currentThread().getContextClassLoader().getResourceAsStream("page.xml") != null)
		{
			configFile = "page.xml";
		}

		this.pageMap = new HashMap<String, String>();
		this.pageElementMap = loadConfig(configFile);

		PageConfigureFileWatchdog pageConfigureFileWatchdog = new PageConfigureFileWatchdog(configFile);
		pageConfigureFileWatchdog.setDaemon(true);
		pageConfigureFileWatchdog.setDelay(60000L);
		pageConfigureFileWatchdog.start();
	}

	public String getConfigByPageName(String pageName, String nodeName)
	{
		String key = pageName + "-" + nodeName;

		if (pageMap.containsKey(key))
		{
			return pageMap.get(key);
		}

		Element element = pageElementMap.get(pageName);

		if (ObjectUtil.isNull(element))
		{
			pageName = "default";
			element = pageElementMap.get(pageName);
		}

		Node nameNode = element.selectSingleNode((new StringBuilder("//page[@name='")).append(pageName).append("']").toString());

		if (ObjectUtil.isNull(nameNode))
		{
			throw new AOException((new StringBuilder("Can not found the page '")).append(nameNode).append("'").toString());
		}

		Node node = nameNode.selectSingleNode((new StringBuilder("//page[@name='")).append(pageName).append("']//").append(nodeName).toString());

		if(ObjectUtil.isNull(node))
        {
            return "";
        }
		else
        {
            pageMap.put(key, node.getText().trim());
            return node.getText().trim();
        }
	}

	@SuppressWarnings("rawtypes")
	private Map<String, Element> loadConfig(String configFile)
	{
		Map<String, Element> pageElementMap = new HashMap<String, Element>();

		try
		{
			SAXReader saxReader = new SAXReader();
			Document document = saxReader.read(Thread.currentThread().getContextClassLoader().getResourceAsStream(configFile));
			Element rootElement = document.getRootElement();

			for (Iterator iterator = rootElement.elementIterator(); iterator.hasNext();)
			{
				Element element = (Element) iterator.next();
				String elementName = element.getName();

				if (elementName.equals("page"))
				{
					String pageName = element.valueOf("@name");

					if (pageElementMap.containsKey(pageName))
					{
						throw new AOException("Page '" + pageName + "' has existed");
					}

					pageElementMap.put(pageName, element);
				}

				if (elementName.equals("include"))
				{
					loadConfig(configFile);
				}
			}
		}
		catch (Exception e)
		{
			throw new AOException("Could not find page config file '" + configFile + "'");
		}

		return pageElementMap;
	}

	class PageConfigureFileWatchdog extends FileWatchdog
	{
		protected PageConfigureFileWatchdog(String filename)
		{
			super(filename);
		}

		@Override
		protected void doOnChange()
		{
			Map<String, Element> map = loadConfig(filename);

			if (ObjectUtil.notEmpty(map))
			{
				pageElementMap = map;
				pageMap = new HashMap<String, String>();
			}

			LogLog.warn((new StringBuilder("Page config load completed from file '")).append(filename).append("'").toString());
		}
	}
}