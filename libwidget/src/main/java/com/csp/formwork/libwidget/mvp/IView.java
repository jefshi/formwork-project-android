/*
 * Copyright 2016, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.csp.formwork.libwidget.mvp;

public interface IView<T> {
    /**
     * 异常处理
     *
     * @param throwable 异常信息
     * @param type      数据处理来源（失败来源，如某个网络请求）
     */
    void onError(Throwable throwable, int type);

    /**
     * 数据加载过程中，页面显示(比如 ProgressBar 显示隐藏)
     *
     * @param showed true: 显示
     */
    void showRequestLoading(boolean showed);

    /**
     * @return 构建 Present 对象
     */
    T createPresent();
}
