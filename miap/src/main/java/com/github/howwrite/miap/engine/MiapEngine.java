package com.github.howwrite.miap.engine;

import com.github.howwrite.miap.def.MiapBook;
import com.github.howwrite.miap.def.MiapBookShelf;
import com.github.howwrite.miap.def.MiapReader;
import com.github.howwrite.treasure.tools.utils.ParameterUtils;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 流程编排引擎
 */
@Component
public class MiapEngine {
    private final List<MiapBook<?>> physicalBookList;
    private final Map<String, MiapBookShelf> bookShelfMap;

    public MiapEngine(@Autowired List<MiapBook<?>> physicalBookList, @Autowired List<MiapBookShelf> miapBookShelfList) {
        this.physicalBookList = physicalBookList;
        this.bookShelfMap = miapBookShelfList.stream().collect(Collectors.toConcurrentMap(MiapBookShelf::readClassification, Function.identity()));
        initBookShelfPhysicalBook(miapBookShelfList, physicalBookList);
    }

    /**
     * 初始化实体执行类
     */
    private void initBookShelfPhysicalBook(List<MiapBookShelf> miapBookShelfList, List<MiapBook<?>> physicalBookList) {
        Map<? extends Class<? extends MiapBook>, MiapBook<?>> bookMapByClass = physicalBookList.stream().collect(Collectors.toMap(it -> it.getClass(), Function.identity()));
        for (MiapBookShelf miapBookShelf : miapBookShelfList) {
            List<MiapBook<?>> onShelfBooks = Lists.newArrayList();
            for (Class<? extends MiapBook<?>> bookClass : miapBookShelf.readBookList()) {
                MiapBook<?> implObject = bookMapByClass.get(bookClass);
                onShelfBooks.add(implObject);
            }
            miapBookShelf.setPhysicalBooks(onShelfBooks);
        }
    }

    public MiapBookShelf findBookShelf(String classification) {
        return bookShelfMap.get(classification);
    }

    public <Postscript> Postscript invoke(@Nonnull String classification, MiapReader<Postscript> reader) {
        ParameterUtils.notBlank("书架名称不可为空", classification);
        MiapBookShelf bookShelf = findBookShelf(classification);
        // todo npe
        bookShelf.invoke(reader);
        return reader.seeTheLast();
    }
}
