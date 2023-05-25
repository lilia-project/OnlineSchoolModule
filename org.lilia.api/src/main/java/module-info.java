module org.lilia.api {
    exports org.lilia.api.network;
    exports org.lilia.api.view;

    requires org.lilia.logger;
    requires org.lilia.dal;
    requires org.lilia.service;
}