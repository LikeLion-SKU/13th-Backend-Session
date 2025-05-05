package com.likelion.springpractice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
class BeanCheckerTest {

  @Autowired
  ApplicationContext context;

  @Test
  void printMyBeansOnly() {
    String[] allBeanNames = context.getBeanDefinitionNames();

    System.out.println("🧩 등록한 Bean 목록");

    for (String beanName : allBeanNames) {
      Object bean = context.getBean(beanName);
      Class<?> beanClass = bean.getClass();

      String packageName = beanClass.getPackageName();

      if (packageName.startsWith("com.likelion.springpractice")) {
        System.out.println("✅ " + beanName + "\n Path: " + beanClass.getName());
      }
    }
  }
}
