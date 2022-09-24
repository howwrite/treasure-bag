package com.github.howwrite.miap.def;

import lombok.Setter;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * 编排流程
 *
 * @author howwrite
 */
public class MiapBookShelf {

    private final String classification;
    private final List<Class<? extends MiapBook<?>>> bookList;

    /**
     * 实体书列表，实体书列表
     */
    @Setter
    private List<MiapBook<?>> physicalBooks;

    public MiapBookShelf(@Nonnull String classification, @Nonnull List<Class<? extends MiapBook<?>>> bookList) {
        this.classification = classification;
        this.bookList = bookList;
    }

    /**
     * 书架编码，对应业务编码，可获取多本书籍
     *
     * @return 当前书架的书架编码
     */
    @Nonnull
    public String readClassification() {
        return classification;
    }

    ;

    /**
     * 书集合
     *
     * @return 书架上的书的集合
     */
    @Nonnull
    public List<Class<? extends MiapBook<?>>> readBookList() {
        return bookList;
    }

    public void invoke(MiapPreface context) {
        for (MiapBook<? extends MiapPreface> physicalBook : physicalBooks) {
            if (physicalBook.canRead(context)) {
                physicalBook.execute(context);
            }
        }
    }
}
