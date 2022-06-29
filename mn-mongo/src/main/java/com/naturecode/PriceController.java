package com.naturecode;

import org.bson.Document;
import org.reactivestreams.Publisher;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoCollection;

import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.reactivex.Flowable;

@Controller("/prices")
public class PriceController {
  private final MongoClient mongoClient;

  public PriceController(MongoClient mongoClient) {
    this.mongoClient = mongoClient;
  }

  @Get("/")
  public Flowable<Document> fetch() {
    var collection = getCollection();
    return Flowable.fromPublisher(collection.find());
  }

  @Post("/")
  public Publisher<InsertOneResult> insert(@Body ObjectNode json) {
    var collection = getCollection();
    final Document doc = Document.parse(json.toString());
    return Flowable.fromPublisher(collection.insertOne(doc));
  }

  private MongoCollection<Document> getCollection() {
    return mongoClient.getDatabase("prices").getCollection("example");
  }
}
