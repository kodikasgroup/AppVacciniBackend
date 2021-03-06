package com.kodikas.appvaccinibackend.controller;

import com.kodikas.appvaccinibackend.model.Entitled;
import com.kodikas.appvaccinibackend.service.EntitledService;
import com.kodikas.appvaccinibackend.wrapper.EntitledWrapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.UnsupportedEncodingException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EntitledControllerUnitTest {

    @Mock
    private EntitledService entitledService;
    private EntitledController underTest;
    private Entitled entry1;
    private Entitled entry2;

    @BeforeEach
    void setUp() {

        underTest = new EntitledController(entitledService);

        entry1 = new Entitled("over80");
        entry2 = new Entitled("over50");
    }

    @Test
    void getAllEntitled() {
        List<Entitled> entitled_list = List.of(entry1,entry2);

        when(entitledService.getAllEntitled()).thenReturn(entitled_list);

        EntitledWrapper result = underTest.getAllEntitled();

        verify(entitledService).getAllEntitled();

        assertEquals(result.getEntitles(),entitled_list);
    }

    @Test
    void addEntitled() {

        when(entitledService.addEntitled(entry2)).thenReturn(entry2);

        Entitled result = underTest.addEntitled(entry2);

        verify(entitledService).addEntitled(entry2);

        assertEquals(entry2,result);
    }

    @Test
    void getAllEntitledByCategory() throws UnsupportedEncodingException {
        List<Entitled> entitledList = List.of(entry1);

        String category = "over80";

        when(entitledService.getEntitledByCategory(category)).thenReturn(entitledList);

        EntitledWrapper result = underTest.getAllEntitledByCategory(category);
        boolean check= false;

        for (Entitled find : result.getEntitles()){

            if(!(find.getCategory().equals(category))){
                check = true;
            }
        }

        assertThat(check).isFalse();
    }
}