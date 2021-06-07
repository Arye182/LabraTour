package Entity.mapper;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import Entity.RatedPoiEntity;

public class RatedPoiEntityJsonMapper {

  private final Gson gson;

  // @Inject
  public RatedPoiEntityJsonMapper() {
    this.gson = new Gson();
  }

  public RatedPoiEntity transforRatedPoiEntity(String ratedPoiEntityJsonResponse)
      throws JsonSyntaxException {
    final Type ratedPoiEntityType = new TypeToken<RatedPoiEntity>() {}.getType();
    return this.gson.fromJson(ratedPoiEntityJsonResponse, ratedPoiEntityType);
  }
    }

