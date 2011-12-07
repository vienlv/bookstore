package org.exoplatform.bookstore.chromattic.entity;
import org.chromattic.apt.Invoker;
import org.chromattic.apt.Instrumented;
public class BookEntity_Chromattic extends BookEntity implements Instrumented {
public final org.chromattic.spi.instrument.MethodHandler handler;
public BookEntity_Chromattic(org.chromattic.spi.instrument.MethodHandler handler) {
this.handler = handler;
}
private static final Invoker method_0 = Invoker.getDeclaredMethod(org.exoplatform.bookstore.chromattic.entity.BookEntity.class,"getIsbn");
public final java.lang.String getIsbn() {
try {
return (java.lang.String)handler.invoke(this,method_0.getMethod());
} catch(Throwable t) {
if (t instanceof java.lang.RuntimeException) throw (java.lang.RuntimeException)t;
if (t instanceof java.lang.Error) throw (java.lang.Error)t;
throw new java.lang.reflect.UndeclaredThrowableException(t);
}
}
private static final Invoker method_1 = Invoker.getDeclaredMethod(org.exoplatform.bookstore.chromattic.entity.BookEntity.class,"setPublisher",java.lang.String.class);
public final void setPublisher(java.lang.String arg_0) {
try {
handler.invoke(this,method_1.getMethod(),(Object)arg_0);
} catch(Throwable t) {
if (t instanceof java.lang.RuntimeException) throw (java.lang.RuntimeException)t;
if (t instanceof java.lang.Error) throw (java.lang.Error)t;
throw new java.lang.reflect.UndeclaredThrowableException(t);
}
}
private static final Invoker method_2 = Invoker.getDeclaredMethod(org.exoplatform.bookstore.chromattic.entity.BookEntity.class,"setCategory",org.exoplatform.bookstore.chromattic.entity.CategoryEntity.class);
public final void setCategory(org.exoplatform.bookstore.chromattic.entity.CategoryEntity arg_0) {
try {
handler.invoke(this,method_2.getMethod(),(Object)arg_0);
} catch(Throwable t) {
if (t instanceof java.lang.RuntimeException) throw (java.lang.RuntimeException)t;
if (t instanceof java.lang.Error) throw (java.lang.Error)t;
throw new java.lang.reflect.UndeclaredThrowableException(t);
}
}
private static final Invoker method_3 = Invoker.getDeclaredMethod(org.exoplatform.bookstore.chromattic.entity.BookEntity.class,"getPath");
public final java.lang.String getPath() {
try {
return (java.lang.String)handler.invoke(this,method_3.getMethod());
} catch(Throwable t) {
if (t instanceof java.lang.RuntimeException) throw (java.lang.RuntimeException)t;
if (t instanceof java.lang.Error) throw (java.lang.Error)t;
throw new java.lang.reflect.UndeclaredThrowableException(t);
}
}
private static final Invoker method_4 = Invoker.getDeclaredMethod(org.exoplatform.bookstore.chromattic.entity.BookEntity.class,"getTitle");
public final java.lang.String getTitle() {
try {
return (java.lang.String)handler.invoke(this,method_4.getMethod());
} catch(Throwable t) {
if (t instanceof java.lang.RuntimeException) throw (java.lang.RuntimeException)t;
if (t instanceof java.lang.Error) throw (java.lang.Error)t;
throw new java.lang.reflect.UndeclaredThrowableException(t);
}
}
private static final Invoker method_5 = Invoker.getDeclaredMethod(org.exoplatform.bookstore.chromattic.entity.BookEntity.class,"getId");
public final java.lang.String getId() {
try {
return (java.lang.String)handler.invoke(this,method_5.getMethod());
} catch(Throwable t) {
if (t instanceof java.lang.RuntimeException) throw (java.lang.RuntimeException)t;
if (t instanceof java.lang.Error) throw (java.lang.Error)t;
throw new java.lang.reflect.UndeclaredThrowableException(t);
}
}
private static final Invoker method_6 = Invoker.getDeclaredMethod(org.exoplatform.bookstore.chromattic.entity.BookEntity.class,"createImage");
public final org.chromattic.ext.ntdef.NTFile createImage() {
try {
return (org.chromattic.ext.ntdef.NTFile)handler.invoke(this,method_6.getMethod());
} catch(Throwable t) {
if (t instanceof java.lang.RuntimeException) throw (java.lang.RuntimeException)t;
if (t instanceof java.lang.Error) throw (java.lang.Error)t;
throw new java.lang.reflect.UndeclaredThrowableException(t);
}
}
private static final Invoker method_7 = Invoker.getDeclaredMethod(org.exoplatform.bookstore.chromattic.entity.BookEntity.class,"setImage",org.chromattic.ext.ntdef.NTFile.class);
public final void setImage(org.chromattic.ext.ntdef.NTFile arg_0) {
try {
handler.invoke(this,method_7.getMethod(),(Object)arg_0);
} catch(Throwable t) {
if (t instanceof java.lang.RuntimeException) throw (java.lang.RuntimeException)t;
if (t instanceof java.lang.Error) throw (java.lang.Error)t;
throw new java.lang.reflect.UndeclaredThrowableException(t);
}
}
private static final Invoker method_8 = Invoker.getDeclaredMethod(org.exoplatform.bookstore.chromattic.entity.BookEntity.class,"getPublisher");
public final java.lang.String getPublisher() {
try {
return (java.lang.String)handler.invoke(this,method_8.getMethod());
} catch(Throwable t) {
if (t instanceof java.lang.RuntimeException) throw (java.lang.RuntimeException)t;
if (t instanceof java.lang.Error) throw (java.lang.Error)t;
throw new java.lang.reflect.UndeclaredThrowableException(t);
}
}
private static final Invoker method_9 = Invoker.getDeclaredMethod(org.exoplatform.bookstore.chromattic.entity.BookEntity.class,"getCategory");
public final org.exoplatform.bookstore.chromattic.entity.CategoryEntity getCategory() {
try {
return (org.exoplatform.bookstore.chromattic.entity.CategoryEntity)handler.invoke(this,method_9.getMethod());
} catch(Throwable t) {
if (t instanceof java.lang.RuntimeException) throw (java.lang.RuntimeException)t;
if (t instanceof java.lang.Error) throw (java.lang.Error)t;
throw new java.lang.reflect.UndeclaredThrowableException(t);
}
}
private static final Invoker method_10 = Invoker.getDeclaredMethod(org.exoplatform.bookstore.chromattic.entity.BookEntity.class,"setTitle",java.lang.String.class);
public final void setTitle(java.lang.String arg_0) {
try {
handler.invoke(this,method_10.getMethod(),(Object)arg_0);
} catch(Throwable t) {
if (t instanceof java.lang.RuntimeException) throw (java.lang.RuntimeException)t;
if (t instanceof java.lang.Error) throw (java.lang.Error)t;
throw new java.lang.reflect.UndeclaredThrowableException(t);
}
}
private static final Invoker method_11 = Invoker.getDeclaredMethod(org.exoplatform.bookstore.chromattic.entity.BookEntity.class,"setIsbn",java.lang.String.class);
public final void setIsbn(java.lang.String arg_0) {
try {
handler.invoke(this,method_11.getMethod(),(Object)arg_0);
} catch(Throwable t) {
if (t instanceof java.lang.RuntimeException) throw (java.lang.RuntimeException)t;
if (t instanceof java.lang.Error) throw (java.lang.Error)t;
throw new java.lang.reflect.UndeclaredThrowableException(t);
}
}
private static final Invoker method_12 = Invoker.getDeclaredMethod(org.exoplatform.bookstore.chromattic.entity.BookEntity.class,"getName");
public final java.lang.String getName() {
try {
return (java.lang.String)handler.invoke(this,method_12.getMethod());
} catch(Throwable t) {
if (t instanceof java.lang.RuntimeException) throw (java.lang.RuntimeException)t;
if (t instanceof java.lang.Error) throw (java.lang.Error)t;
throw new java.lang.reflect.UndeclaredThrowableException(t);
}
}
private static final Invoker method_13 = Invoker.getDeclaredMethod(org.exoplatform.bookstore.chromattic.entity.BookEntity.class,"getImage");
public final org.chromattic.ext.ntdef.NTFile getImage() {
try {
return (org.chromattic.ext.ntdef.NTFile)handler.invoke(this,method_13.getMethod());
} catch(Throwable t) {
if (t instanceof java.lang.RuntimeException) throw (java.lang.RuntimeException)t;
if (t instanceof java.lang.Error) throw (java.lang.Error)t;
throw new java.lang.reflect.UndeclaredThrowableException(t);
}
}
}
