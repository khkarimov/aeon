package framework_abstraction;

import common.webobjects.interfaces.IBy;
import framework_abstraction.webdriver.IWebElementAdapter;

/**
 Represents the web element at the facade.
*/
//C# TO JAVA CONVERTER WARNING: Java does not allow user-defined value types. The behavior of this class will differ from the original:
//ORIGINAL LINE: public struct WebElement : IEquatable<WebElement>
	public final class WebElement
	{
		private IWebElementAdapter webElementAdapter;
		private IBy selector;

		public WebElement()
		{
		}

		public WebElement(IWebElementAdapter webElementAdapter, IBy selector)
		{
			this.webElementAdapter = webElementAdapter;
			this.selector = selector;
		}

		public IWebElementAdapter getWebElementAdapter()
		{
			return webElementAdapter;
		}

		public IBy getSelector()
		{
			return selector;
		}

		/** 
		 Determines whether two specified <see cref="WebElement"/> objects have the same value.
		 
		 @param x A <see cref="WebElement"/> or <see langword="null"/>.
		 @param y A <see cref="WebElement"/> or <see langword="null"/>.
		 @return <see langword="true"/> if the value of <paramref name="x"/> is the same as the value of <paramref name="y"/>; otherwise, <see langword="false"/>.
		*/
		public static boolean OpEquality(WebElement x, WebElement y)
		{
			return x.equals(y.clone());
		}

		/** 
		 Determines whether two specified <see cref="WebElement"/> objects have different values.
		 
		 @param x A <see cref="WebElement"/> or <see langword="null"/>.
		 @param y A <see cref="WebElement"/> or <see langword="null"/>.
		 @return <see langword="true"/> if the value of <paramref name="x"/> is different from the value of <paramref name="y"/>; otherwise, <see langword="false"/>.
		*/
		public static boolean OpInequality(WebElement x, WebElement y)
		{
			return !x.equals(y.clone());
		}

		/** 
		 Indicates whether this instance and a specified object are equal.
		 
		 @return true if <paramref name="obj"/> and this instance are the same type and represent the same value; otherwise, false.
		 @param obj Another object to compare to. <filterpriority>2</filterpriority>
		*/
		@Override
		public boolean equals(Object obj)
		{
			if (obj == null)
			{
				return false;
			}

			return obj instanceof WebElement && super.equals(obj);
		}

		/** 
		 Returns the hash code for this instance.
		 
		 @return A 32-bit signed integer that is the hash code for this instance.
		 <filterpriority>2</filterpriority>
		*/
		@Override
		public int hashCode()
		{
			return webElementAdapter == null ? 0 : webElementAdapter.hashCode();
		}

		public WebElement clone()
		{
			WebElement varCopy = new WebElement();

			varCopy.webElementAdapter = this.webElementAdapter;
			varCopy.selector = this.selector;

			return varCopy;
		}
	}
