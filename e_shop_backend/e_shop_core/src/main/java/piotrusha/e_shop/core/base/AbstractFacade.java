package piotrusha.e_shop.core.base;

import io.vavr.Tuple;
import io.vavr.Tuple2;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class AbstractFacade<Entity, Dto> {

    protected <DtoImpl extends Dto> Entity performAction(Function<Tuple2<DtoImpl, Entity>, Entity> action, DtoImpl dto) {
        Tuple2<DtoImpl, Entity> t = readEntities(dto);
        Entity entity = action.apply(t);
        save(entity);
        return entity;
    }

    protected <DtoImpl extends Dto> List<Entity> performAction(Function<List<Tuple2<DtoImpl, Entity>>, List<Entity>> action,
                                                               List<DtoImpl> dtos) {
        List<Tuple2<DtoImpl, Entity>> t = readEntities(dtos);
        List<Entity> entities = action.apply(t);
        saveAll(entities);
        return entities;
    }

    private <DtoImpl extends Dto> List<Tuple2<DtoImpl, Entity>> readEntities(List<DtoImpl> dtos) {
        return dtos.stream()
                   .map(this::readEntities)
                   .collect(Collectors.toList());
    }

    private <DtoImpl extends Dto> Tuple2<DtoImpl, Entity> readEntities(DtoImpl dto) {
        Entity entity = findEntity(dto);
        return Tuple.of(dto, entity);
    }

    protected abstract Entity findEntity(Dto dto);

    protected abstract void save(Entity entity);

    protected abstract void saveAll(List<Entity> entities);

}
