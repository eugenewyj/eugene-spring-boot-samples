module org.eugene.person.test {
    requires org.eugene.mod.person;

    //如果在org.eugene.mod.person中声明requires transitive org.eugene.mod.address，则可以不添加 org.eugene.mod.address。
    requires org.eugene.mod.address;
}