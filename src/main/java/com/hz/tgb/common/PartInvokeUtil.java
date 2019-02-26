package com.hz.tgb.common;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * 分批执行工具类
 *
 * Created by hezhao on 2019/1/18 20:27
 */
public class PartInvokeUtil {

    /**
     * 分批获取结果集
     * @param invoke 执行函数
     * @param ids id集合
     * @param limit 一批最大查询的次数
     * @param <P> id
     * @param <V> 结果
     * @return 结果集
     */
    public static <P,V> List<V> partGet(Function<List<P>, List<V>> invoke, List<P> ids, int limit) {
        if (CollectionUtils.isEmpty(ids)) {
            return new ArrayList<>();
        }
        int size = ids.size();
        List<V> list = new ArrayList<>();
        List<P> subIds;
        if (size > limit) {
            int count = size / limit;
            if (size % limit != 0) count += 1;
            int length = 0;
            for (int i = 0; i < count; i++) {
                if (i == count - 1) {
                    limit = size - (i * limit);
                }
                subIds = ids.subList(length, length + limit);
                length += limit;
                if (CollectionUtils.isEmpty(subIds)) {
                    break;
                }
                list.addAll(invoke.apply(subIds));
            }
        } else {
            list.addAll(invoke.apply(ids));
        }
        return list;
    }

    /**
     * 分批获取结果集
     * @param invoke 执行函数
     * @param ids id数组
     * @param limit 一批最大查询的次数
     * @param <P> id
     * @param <V> 结果
     * @return 结果集
     */
    public static <P,V> List<V> partGet(Function<P[], List<V>> invoke, P[] ids, int limit) {
        if (ArrayUtils.isEmpty(ids)) {
            return new ArrayList<>();
        }
        int size = ids.length;
        List<V> list = new ArrayList<>();
        P[] subIds;
        if (size > limit) {
            int count = size / limit;
            if (size % limit != 0)  count += 1;
            int length = 0;
            for (int i = 0; i < count; i++) {
                if (i == count - 1) {
                    limit = size - (i * limit);
                }
                subIds = Arrays.copyOfRange(ids, length, length + limit);
                length += limit;
                if (ArrayUtils.isEmpty(subIds)) {
                    break;
                }
                list.addAll(invoke.apply(subIds));
            }
        } else {
            list.addAll(invoke.apply(ids));
        }
        return list;
    }

    /**
     * 分批执行
     * @param invoke 执行函数
     * @param rows 结果集集合
     * @param limit 一批最大执行的次数
     * @param <T> 结果
     */
    public static <T> void partInvoke(Consumer<List<T>> invoke, List<T> rows, int limit) {
        if (CollectionUtils.isEmpty(rows)) {
            return;
        }
        int size = rows.size();
        List<T> subRows;
        if (size > limit) {
            int count = size / limit;
            if (size % limit != 0) count += 1;
            int length = 0;
            for (int i = 0; i < count; i++) {
                if (i == count - 1) {
                    limit = size - (i * limit);
                }
                subRows = rows.subList(length, length + limit);
                length += limit;
                if (CollectionUtils.isEmpty(subRows)) {
                    break;
                }
                invoke.accept(subRows);
            }
        } else {
            invoke.accept(rows);
        }
    }

    /**
     * 分批执行
     * @param invoke 执行函数
     * @param rows 结果集数组
     * @param limit 一批最大执行的次数
     * @param <T> 结果
     */
    public static <T> void partInvoke(Consumer<T[]> invoke, T[] rows, int limit) {
        if (ArrayUtils.isEmpty(rows)) {
            return;
        }
        int size = rows.length;
        T[] subRows;
        if (size > limit) {
            int count = size / limit;
            if (size % limit != 0) count += 1;
            int length = 0;
            for (int i = 0; i < count; i++) {
                if (i == count - 1) {
                    limit = size - (i * limit);
                }
                subRows = Arrays.copyOfRange(rows, length, length + limit);
                length += limit;
                if (ArrayUtils.isEmpty(subRows)) {
                    break;
                }
                invoke.accept(subRows);
            }
        } else {
            invoke.accept(rows);
        }
    }

    /**
     * 分批执行
     * @param invoke 执行函数
     * @param invokeModel 执行参数，结果集和Id集合
     * @param limit 一批最大执行的次数
     * @param <P> id
     * @param <V> 结果
     */
    public static <P, V> void partInvoke(Consumer<InvokeModel<P, V>> invoke, InvokeModel<P, V> invokeModel, int limit) {
        if (invokeModel == null || CollectionUtils.isEmpty(invokeModel.getRows()) || ArrayUtils.isEmpty(invokeModel.getIds())) {
            return;
        }
        P[] ids = invokeModel.getIds();
        int size = ids.length;
        if (size > limit) {
            int count = size / limit;
            if (size % limit != 0)  count += 1;
            int length = 0;
            for (int i = 0; i < count; i++) {
                if (i == count - 1) {
                    limit = size - (i * limit);
                }
                P[] subIds = Arrays.copyOfRange(ids, length, length + limit);
                length += limit;
                invokeModel.setIds(subIds);
                if (ArrayUtils.isEmpty(invokeModel.getIds())) {
                    break;
                }
                invoke.accept(invokeModel);
            }
        } else {
            invoke.accept(invokeModel);
        }
    }

    /**
     * 分批请求实体
     * @param <P> id
     * @param <V> 结果
     */
    public static class InvokeModel<P, V> {
        /** id数组 */
        private P[] ids;
        /** 结果集 */
        private List<V> rows;

        public P[] getIds() {
            return ids;
        }

        public void setIds(P[] ids) {
            this.ids = ids;
        }

        public List<V> getRows() {
            return rows;
        }

        public void setRows(List<V> rows) {
            this.rows = rows;
        }
    }

}
