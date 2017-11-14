package net.hugonardo.android.commons.content;

import android.content.ContentValues;
import android.database.Cursor;
import android.support.annotation.NonNull;

/**
 * Esta interface tem como objetivo definir dois métodos importantes a
 * qualquer entidade do ContentResolver:
 * <p>
 * - toContentValues(boolean): define que uma entidade do ContentResolver
 * deve saber se serializar em ContentValues
 * <p>
 * - fillFromCursor(Cursor): define que uma entidade do ContentResolver
 * deve saber se desserializar a partir de um Cursor.
 */
public interface ContentResolverEntity<T> {

    /**
     * A entidade que implementa este método deve se desserializar a partir de um {@link Cursor}
     * e retornar uma auto-referência:
     * <p>
     * <pre>
     * public Obj fillFromCursor(Cursor cursor) {
     *      // desserialize
     *      return this;
     * }
     * </pre>
     * <p>
     * Deve-se ainda ficar atento para que todas as propriedades do objeto desserializado
     * sejam refletidas. Ou seja, atribuir null a todos os atributos que não obtiverem valor
     * a partir do cursor.
     * <p>
     * Recomenda-se utilizar o {@link CursorReader} para auxiliar este trabalho.
     *
     * @param cursor um Cursor
     * @return uma auto-referência ao objeto
     */
    T fillFromCursor(@NonNull Cursor cursor);

    /**
     * A entidade que implementa este método deve serializar seus valores em um ContentValues.
     * <p>
     * Recomenda-se utilizar o {@link ContentValuesBuilder} para auxiliar este trabalho.
     *
     * @param onlyNonNull {@code true} se devem ser serializados apenas os atributos não-nulos
     * @return um {@link ContentValues} com os valores deste objeto
     */
    @NonNull
    ContentValues toContentValues(boolean onlyNonNull);
}
