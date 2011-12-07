package org.exoplatform.bookstore.chromattic.entity;
import org.chromattic.apt.Invoker;
import org.chromattic.apt.Instrumented;
public class CategoryEntity_Chromattic extends CategoryEntity implements Instrumented {
public final org.chromattic.spi.instrument.MethodHandler handler;
public CategoryEntity_Chromattic(org.chromattic.spi.instrument.MethodHandler handler) {
this.handler = handler;
}
private static final Invoker method_0 = Invoker.getDeclaredMethod(org.exoplatform.bookstore.chromattic.entity.CategoryEntity.class,"createBook");
public final org.exoplatform.bookstore.chromattic.entity.BookEntity createBook() {
try {
return (org.exoplatform.bookstore.chromattic.entity.BookEntity)handler.invoke(this,method_0.getMethod());
} catch(Throwable t) {
if (t instanceof java.lang.RuntimeException) throw (java.lang.RuntimeException)t;
if (t instanceof java.lang.Error) throw (java.lang.Error)t;
throw new java.lang.reflect.UndeclaredThrowableException(t);
}
}
private static final Invoker method_1 = Invoker.getDeclaredMethod(org.exoplatform.bookstore.chromattic.entity.CategoryEntity.class,"getBooks");
public final java.util.Map getBooks() {
try {
return (java.util.Map)handler.invoke(this,method_1.getMethod());
} catch(Throwable t) {
if (t instanceof java.lang.RuntimeException) throw (java.lang.RuntimeException)t;
if (t instanceof java.lang.Error) throw (java.lang.Error)t;
throw new java.lang.reflect.UndeclaredThrowableException(t);
}
}
private static final Invoker method_2 = Invoker.getDeclaredMethod(org.exoplatform.bookstore.chromattic.entity.CategoryEntity.class,"getCategoryRoot");
public final org.exoplatform.bookstore.chromattic.entity.CategoryRootEntity getCategoryRoot() {
try {
return (org.exoplatform.bookstore.chromattic.entity.CategoryRootEntity)handler.invoke(this,method_2.getMethod());
} catch(Throwable t) {
if (t instanceof java.lang.RuntimeException) throw (java.lang.RuntimeException)t;
if (t instanceof java.lang.Error) throw (java.lang.Error)t;
throw new java.lang.reflect.UndeclaredThrowableException(t);
}
}
private static final Invoker method_3 = Invoker.getDeclaredMethod(org.exoplatform.bookstore.chromattic.entity.CategoryEntity.class,"getName");
public final java.lang.String getName() {
try {
return (java.lang.String)handler.invoke(this,method_3.getMethod());
} catch(Throwable t) {
if (t instanceof java.lang.RuntimeException) throw (java.lang.RuntimeException)t;
if (t instanceof java.lang.Error) throw (java.lang.Error)t;
throw new java.lang.reflect.UndeclaredThrowableException(t);
}
}
private static final Invoker method_4 = Invoker.getDeclaredMethod(org.exoplatform.bookstore.chromattic.entity.CategoryEntity.class,"setCategoryRoot",org.exoplatform.bookstore.chromattic.entity.CategoryRootEntity.class);
public final void setCategoryRoot(org.exoplatform.bookstore.chromattic.entity.CategoryRootEntity arg_0) {
try {
handler.invoke(this,method_4.getMethod(),(Object)arg_0);
} catch(Throwable t) {
if (t instanceof java.lang.RuntimeException) throw (java.lang.RuntimeException)t;
if (t instanceof java.lang.Error) throw (java.lang.Error)t;
throw new java.lang.reflect.UndeclaredThrowableException(t);
}
}
private static final Invoker method_5 = Invoker.getDeclaredMethod(org.exoplatform.bookstore.chromattic.entity.CategoryEntity.class,"getPath");
public final java.lang.String getPath() {
try {
return (java.lang.String)handler.invoke(this,method_5.getMethod());
} catch(Throwable t) {
if (t instanceof java.lang.RuntimeException) throw (java.lang.RuntimeException)t;
if (t instanceof java.lang.Error) throw (java.lang.Error)t;
throw new java.lang.reflect.UndeclaredThrowableException(t);
}
}
private static final Invoker method_6 = Invoker.getDeclaredMethod(org.exoplatform.bookstore.chromattic.entity.CategoryEntity.class,"getId");
public final java.lang.String getId() {
try {
return (java.lang.String)handler.invoke(this,method_6.getMethod());
} catch(Throwable t) {
if (t instanceof java.lang.RuntimeException) throw (java.lang.RuntimeException)t;
if (t instanceof java.lang.Error) throw (java.lang.Error)t;
throw new java.lang.reflect.UndeclaredThrowableException(t);
}
}
}
